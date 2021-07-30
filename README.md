Library to add documents on firebase Store-

This is library is for uploading document in your firebase storage.

Step by Step guide to use this Library into your project is as follows-

1.	Download the library AAR file from the following link https://drive.google.com/drive/folders/18uxGcYDox4gBVzaMYgGZC7YxopoOQy-V?usp=sharing.

2.	Now add the aar file in libs folder in your project. Go to project/ app/libs.

3.	Now add the google services and the dir location of the aar file in build.gradle.
 
4.	Now add the following line of code in build.gradle.app.
    implementation 'com.firebaseui:firebase-ui-firestore:6.3.0'
    implementation 'com.google.firebase:firebase-firestore:23.0.3'
    implementation 'com.firebaseui:firebase-ui-auth:6.3.0'
    implementation 'com.firebaseui:firebase-ui-storage:6.3.0'
    implementation(name:'invoidLibrary', ext:'aar')
    And also add google service at bottom.
    apply plugin: 'com.google.gms.google-services'

5.	Now wherever you want to call the library add this line of code.
   Intent intent=new Intent(AddDetails.this, UploadDocumentActivity.class);
   startActivity(intent);

6.	Also don’t forget to connect your firebase to your application.

7.	Once the uploading is completing and get the url of the location add this line of code wherever required.
    SharedPreferences mySharedPreferences = getSharedPreferences("URLLINK", Context.MODE_PRIVATE);
    imageUrl = mySharedPreferences.getString("URL", "");

8.	You are Good to go.

Note- There are easy ways to add a library but in the task it was mentioned to add it from AAR file so I have used it.

9.	You can check the code of the example application where this library is used. The link is as follows-
https://github.com/Kartikey2235/Invoid_Library_Application_Example	


Thank You.
