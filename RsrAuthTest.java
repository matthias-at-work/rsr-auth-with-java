/**
 * Dummy example to use JNA to access RSR-auth library.
 * Matthias Bader 09-JUN-2019
 */

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.nio.*;

class RsrAuthTest {
    
    public interface CLibrary extends Library {

        CLibrary INSTANCE = (CLibrary) Native.load("libfsrverify.so.1.1.4", CLibrary.class); 
	
		// DECLSPEC FSRTOKEN_STATUS __stdcall fsrverify_InitLoadFromFile(
		//   LPCWSTR keystoreFile);
		int fsrverify_InitLoadFromFile(
			com.sun.jna.WString keystoreFile);

		// The fsrverify_VerifyToken verifies if a given string is a valid token for a given username. If the
		// given string has the format of the token, it also returns the information contained in the token. The
		// function returns 0 if the given token is valid and the key used to verify the token's signature has not
		// been revoked. If the token is valid but revocation checking is disabled, 17 is returned.
		//
		// DECLSPEC FSRTOKEN_STATUS __stdcall fsrverify_VerifyToken(
        //   LPCWSTR username,
        //   LPCWSTR tokenString,
		//   int* keyIndex, 
		//   int* expiryDate);
		int fsrverify_VerifyToken(
			com.sun.jna.WString username, 
			com.sun.jna.WString tokenString, 
			com.sun.jna.ptr.IntByReference keyIndex, 
			com.sun.jna.ptr.IntByReference expiryDate);
	}

    public static void main(String[] args) {
		
		// instantiate library from key-file
		com.sun.jna.WString filePath = new com.sun.jna.WString("//home//mats//myjava//fsr-authentication.public.keys");
        CLibrary.INSTANCE.fsrverify_InitLoadFromFile(filePath);
		
		// call verification method
		com.sun.jna.WString username = new com.sun.jna.WString("TestFSR1");
		com.sun.jna.WString tokenString = new com.sun.jna.WString("BTLHW-XYH2Y-WONET-6URQ");
		com.sun.jna.ptr.IntByReference keyIndex = new com.sun.jna.ptr.IntByReference();
		com.sun.jna.ptr.IntByReference expiryDate = new com.sun.jna.ptr.IntByReference();
        int tokenStatus = CLibrary.INSTANCE.fsrverify_VerifyToken(username, tokenString, keyIndex, expiryDate);

		// visualize result
		System.out.println("Token-status: " + tokenStatus);
		System.out.println("Key-index: " + keyIndex.getValue());
		// Docu: expiration date => number of days since 1st January 2011
		System.out.println("Expiry-date (number of days since 1st January 2011): " + expiryDate.getValue());
		System.out.println("program terminated successfully...");
    }
}
