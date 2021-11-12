#include "com_siva_NtfsSet.h"
#include <iostream>
#include <Windows.h>
#include <vector>
#include <map>
#include <fstream>
#include <iostream>
#include <aclapi.h>
#include <windows.h>
#include <string>
#include <memory>
#include <tchar.h>


using namespace std;

DWORD AddAceToObjectsSecurityDescriptor(
     LPTSTR pszObjName,          // name of object
     SE_OBJECT_TYPE ObjectType,  // type of object     
     DWORD dwAccessRights,       // access mask for new ACE
     ACCESS_MODE AccessMode,     // type of ACE
     DWORD dwInheritance         // inheritance flags for new ACE
 )
 {
     DWORD dwRes = 0;
     PACL pOldDACL = NULL, pNewDACL = NULL;
     PSECURITY_DESCRIPTOR pSD = NULL;
     EXPLICIT_ACCESS ea;

     if (NULL == pszObjName)
         return ERROR_INVALID_PARAMETER;

     // Get a pointer to the existing DACL.

     dwRes = GetNamedSecurityInfo(pszObjName, ObjectType,
         DACL_SECURITY_INFORMATION,
         NULL, NULL, &pOldDACL, NULL, &pSD);

     if (ERROR_SUCCESS != dwRes) {
         printf("GetNamedSecurityInfo Error %u\n", dwRes);
         goto Cleanup;
     }


     // Initialize an EXPLICIT_ACCESS structure for the new ACE. 

     ZeroMemory(&ea, sizeof(EXPLICIT_ACCESS));
     ea.grfAccessPermissions = dwAccessRights;
     ea.grfAccessMode = AccessMode;
     ea.grfInheritance = dwInheritance;
     ea.Trustee.TrusteeForm = TRUSTEE_IS_NAME;
     ea.Trustee.ptstrName = _T("CURRENT_USER");
     ea.Trustee.TrusteeType = TRUSTEE_IS_USER;
     // Create a new ACL that merges the new ACE
     // into the existing DACL.

     dwRes = SetEntriesInAcl(1, &ea, pOldDACL, &pNewDACL);
     if (ERROR_SUCCESS != dwRes) {
         printf("SetEntriesInAcl Error %u\n", dwRes);
         goto Cleanup;
     }

     // Attach the new ACL as the object's DACL.

     dwRes = SetNamedSecurityInfo(pszObjName, ObjectType,
         DACL_SECURITY_INFORMATION,
         NULL, NULL, pNewDACL, NULL);
     if (ERROR_SUCCESS != dwRes) {
         printf("SetNamedSecurityInfo Error %u\n", dwRes);
         goto Cleanup;
     }

 Cleanup:

     if (pSD != NULL)
         LocalFree((HLOCAL)pSD);
     if (pNewDACL != NULL)
         LocalFree((HLOCAL)pNewDACL);

     return dwRes;
 }



bool grantAccess(std::string file, int permissionval) {
    
    DWORD grant = 0;

    unsigned int permissionset = 0x00;

    cout << "permissionset : " << permissionset << endl << "permssionval : " << permissionval << endl;


    if ((permissionval & 1) == 1) {
        permissionset |= FILE_GENERIC_WRITE;
        cout << "inside 1 : " << permissionset << endl;
    }
    if ((permissionval & 2) == 2) {
        permissionset |= FILE_GENERIC_READ;
        cout << "inside 2 : " << permissionset << endl;
    }
    if ((permissionval & 4) == 4) {
        permissionset |= FILE_GENERIC_EXECUTE;
        cout << "inside 4 : " << permissionset << endl;
    }
    if ((permissionval & 8) == 8) {
        permissionset |= FILE_ALL_ACCESS;
        cout << "inside 8 : " << permissionset << endl;
    }
    if (permissionval == 0) {
        permissionset |= NOT_USED_ACCESS;
        cout << "inside 0 : " << permissionset << endl;
    }
  
    LPTSTR lpfile = new TCHAR[31];
    lpfile = (LPTSTR) file.c_str();


      DWORD dwres1 = AddAceToObjectsSecurityDescriptor(
         lpfile,
         SE_FILE_OBJECT,
         permissionset,
         SET_ACCESS,
         NO_INHERITANCE);

     if (dwres1 == ERROR_SUCCESS)
     {
        return TRUE;
     }
     else
     {
         std::cout << "Error   :   " << GetLastError() << '\n';
     }


     return FALSE;
}


JNIEXPORT jboolean JNICALL Java_com_siva_NtfsSet_setPermission
  (JNIEnv * env, jobject obj, jstring file, jint permissionval ) {

    const char * file1 = env->GetStringUTFChars(file, 0);
    std::string filestr(file1);
    return grantAccess(filestr, permissionval);
  }