#include "com_siva_CreateDirectory.h"
#include<windows.h>
#include<fileapi.h>
#include<iostream>

using namespace std;

bool createDirectory(std::string dirPath, std::string dirName) {

    std::string createdir = dirPath + "\\" + dirName;

    BOOL flag = CreateDirectoryA(createdir.c_str(), NULL);

    if (flag) {
        std::cout << "Directory Created" << endl;
    } else {
        switch (GetLastError())
        {
        case ERROR_ALREADY_EXISTS:
            std::cout << "ERR: Directory already exists" << endl;
            break;
        case ERROR_PATH_NOT_FOUND:
            std::cout << "ERR: Path not found" << endl;
            break;
        default:
            break;
        }
        return false;
    }
    return true;
}

JNIEXPORT jboolean JNICALL Java_com_siva_CreateDirectory_createDirectory
  (JNIEnv * env, jobject obj, jstring dirPath, jstring dirName) {

  const char * directoryPath = env->GetStringUTFChars(dirPath, 0);
  const char * directoryName = env->GetStringUTFChars(dirName, 0);
  std::string dirPathStr(directoryPath);
  std::string dirNameStr(directoryName);
  env->ReleaseStringUTFChars(dirPath, directoryPath);
  env->ReleaseStringUTFChars(dirName, directoryName);
  return createDirectory(dirPathStr, dirNameStr);
}