#include "com_siva_Ntfs.h"
#include <iostream>
#include <vector>
#include <string>
#include <windows.h>
#include <map>
#include <fstream>

using namespace std;

bool CanAccessFolder(LPCTSTR folderName, DWORD genericAccessRights,DWORD& grantedRights)
{
    // cout << "canAccessFolder called" << std::endl;
    // cout << "foldername : " << folderName << std::endl;
    bool bRet = false;
    DWORD length = 0;
    if (!::GetFileSecurity(folderName, OWNER_SECURITY_INFORMATION | GROUP_SECURITY_INFORMATION
        | DACL_SECURITY_INFORMATION, NULL, NULL, &length) &&
        ERROR_INSUFFICIENT_BUFFER == ::GetLastError()) {
//             cout << "Inside 1st if" << std::endl;
        PSECURITY_DESCRIPTOR security = static_cast< PSECURITY_DESCRIPTOR >(::malloc(length));
        if (security && ::GetFileSecurity(folderName, OWNER_SECURITY_INFORMATION | GROUP_SECURITY_INFORMATION
            | DACL_SECURITY_INFORMATION, security, length, &length)) {
                // cout << "Inside 2nd if" << std::endl;
            HANDLE hToken = NULL;
            if (::OpenProcessToken(::GetCurrentProcess(), TOKEN_IMPERSONATE | TOKEN_QUERY |
                TOKEN_DUPLICATE | STANDARD_RIGHTS_READ, &hToken)) {
                    // cout << "Inside 3rd if" << std::endl;
                HANDLE hImpersonatedToken = NULL;
                if (::DuplicateToken(hToken, SecurityImpersonation, &hImpersonatedToken)) {
                    // cout << "Inside 4th if" << std::endl;
                    GENERIC_MAPPING mapping = { 0xFFFFFFFF };
                    PRIVILEGE_SET privileges = { 0 };
                    DWORD grantedAccess = 0, privilegesLength = sizeof(privileges);
                    BOOL result = FALSE;

                    mapping.GenericRead = FILE_GENERIC_READ;
                    mapping.GenericWrite = FILE_GENERIC_WRITE;
                    mapping.GenericExecute = FILE_GENERIC_EXECUTE;
                    mapping.GenericAll = FILE_ALL_ACCESS;

                    ::MapGenericMask(&genericAccessRights, &mapping);
                    if (::AccessCheck(security, hImpersonatedToken, genericAccessRights,
                        &mapping, &privileges, &privilegesLength, &grantedAccess, &result)) 
                    {
                        // cout << "Inside 5th if" << std::endl;
                        bRet = (result == TRUE);
                        // cout << "bRet : " << bRet << "\t" << "result : " << result << std::endl;
                        grantedRights = grantedAccess;
                        // cout << "granted rights : " << grantedRights << "\t" << grantedAccess << std::endl;
                    }
                    ::CloseHandle(hImpersonatedToken);
                }
                ::CloseHandle(hToken);
            }
            ::free(security);
        }
    }

    return bRet;
}

 vector<string> printMasks(DWORD Mask)
 {
     // This evaluation of the ACCESS_MASK is an example.
     // Applications should evaluate the ACCESS_MASK as necessary.
     vector<string> access;
    std::wcout << "Effective Allowed Access Mask :  "<< Mask << std::hex << std::endl;
     if (((Mask & GENERIC_ALL) == GENERIC_ALL)
         || ((Mask & FILE_ALL_ACCESS) == FILE_ALL_ACCESS))
     {
        //  wprintf_s(L"Full Control\n");
         access.push_back("Full Control");
        //  return access;
     }
     if (((Mask & GENERIC_READ) == GENERIC_READ)
         || ((Mask & FILE_GENERIC_READ) == FILE_GENERIC_READ))
        //  wprintf_s(L"Read\n");
         access.push_back("Read");
     if (((Mask & GENERIC_WRITE) == GENERIC_WRITE)
         || ((Mask & FILE_GENERIC_WRITE) == FILE_GENERIC_WRITE))
        //  wprintf_s(L"Write\n");
         access.push_back("Write");
     if (((Mask & GENERIC_EXECUTE) == GENERIC_EXECUTE)
         || ((Mask & FILE_GENERIC_EXECUTE) == FILE_GENERIC_EXECUTE))
        //  wprintf_s(L"Execute\n");
         access.push_back("Execute");

    return access;
 }
 
 
std::map<std::string, std::vector<std::string>>
list_directory(const std::string &directory)
{
    DWORD access_mask = FILE_GENERIC_READ | FILE_GENERIC_WRITE | FILE_GENERIC_EXECUTE | FILE_ALL_ACCESS;
    std::map<std::string, std::vector<std::string>> files;
    WIN32_FIND_DATAA findData;
    HANDLE hFind = INVALID_HANDLE_VALUE;
    std::string full_path = directory + "\\*";

    hFind = FindFirstFileA(full_path.c_str(), &findData);

    if (hFind == INVALID_HANDLE_VALUE)
        throw std::runtime_error("Invalid handle value! Please check your path...");

    while (FindNextFileA(hFind, &findData) != 0)
    {
        std::string file = findData.cFileName;
        std::string filepath = directory + "/" + file;
        DWORD grant = 0;
        bool b = CanAccessFolder(filepath.c_str(), access_mask, grant);
        files[file] = printMasks(grant);
    }

    FindClose(hFind);

    return files;
}



JNIEXPORT void JNICALL Java_com_siva_Ntfs_get_1all_1files_1names_1within_1folder
  (JNIEnv * env, jobject obj, jstring folder) {
    
    std::map<std::string, std::vector<std::string>> files;
    const char* path = env->GetStringUTFChars(folder, 0);
    files = list_directory(path);
    
    ofstream outfile;
    outfile.open("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\outfile.dat", ios::out | ios::trunc );
    int i = 0;

    map<string, vector<string>> :: iterator it=files.begin();
    //iteration using traditional for loop
    for(it=files.begin();it!=files.end();it++)
    {
        //accessing keys
        // cout << it->first << ",";
        outfile << i++ << "," << it->first << ",";
        //accessing values (vectors)
        for (auto &&i : it->second)
        {
            // cout << i << ":";
            outfile << i << ":";
        }
        // cout << endl;
        outfile << endl;
        
    }
    
    outfile.close();
  }