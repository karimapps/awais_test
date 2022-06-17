package com.karimapps.mliving.fcmodels;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.karimapps.mliving.Common.AppController.mRef;


/**
 * Created by umar on 11/18/16.
 */

public class UserActions {


    public interface UserExistsInterface {
        void userExist(boolean exists);
    }


//    public void saveUser(String uid, User_dto user_dto, DatabaseReference.CompletionListener completionListener) {
//        mRef.child(uid).updateChildren(user_dto.toMap(), completionListener);
//    }


    public void updateUser(String uid, String displayName, String aboutMe, String accountType, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("displayName", displayName);
        result.put("aboutMe", aboutMe);
        result.put("accountType", accountType);

        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void saveUser(String uid, String fullname, String phone, String email, String pass, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("fullname", fullname);
        result.put("phone", phone);
        result.put("email", email);
        result.put("pass", pass);


        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void saveUser2(String uid, String fullname, String is_pro, String email, String pass, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("fullname", fullname);
        result.put("is_pro", is_pro);
        result.put("email", email);
        result.put("pass", pass);


        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void updateUserDetail(String uid, String email, String displayName, String username, String profilePicUrl, String accountType, String is_active, String isOnline, String token, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("email", email);
        result.put("name", displayName);
        result.put("username", username);
        result.put("profilePicUrl", profilePicUrl);
        result.put("accountType", accountType);
        result.put("is_active", is_active);
        result.put("is_online", isOnline);
        result.put("token", token);


        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void upDateIsOnline(String uid, String isOnline, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("is_online", isOnline);

        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void upDateToken(String uid, String isOnline, String token, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("is_online", isOnline);
        result.put("token", token);


        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void updateWins(String uid, String wins, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();


        result.put("wins", wins);

        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void updateLoss(String uid, String wins, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();


        result.put("loss", wins);

        mRef.child(uid).updateChildren(result, completionListener);
    }


//    public static void updateWins(String uid, String wins, DatabaseReference.CompletionListener completionListener) {
//
//        HashMap<String, Object> result = new HashMap<>();
//
//        result.put("wins", wins);
//
//
//        mRef.child(uid).updateChildren(result, completionListener);
//    }

    public void updateUser1(String uid, String displayName, String firstname, String lastname, String aboutMe, String accountType,
                            String gender, String profilePicUrl, String profileCoverUrl, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("displayName", displayName);
        result.put("aboutMe", aboutMe);
        result.put("accountType", accountType);
        result.put("firstName", firstname);
        result.put("lastName", lastname);
        result.put("gender", gender);
        result.put("profilePicUrl", profilePicUrl);
        result.put("profileCoverUrl", profileCoverUrl);
        result.put("timeCreated", ServerValue.TIMESTAMP);

        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void updateUserName(String uid, String displayName, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("username", displayName);

        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void updateUserProfileImage(String uid, String url, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("profilePicUrl", url);


        mRef.child(uid).updateChildren(result, completionListener);
    }

    public void updateUserCoverImage(String uid, String url, DatabaseReference.CompletionListener completionListener) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("profileCoverUrl", url);


        mRef.child(uid).updateChildren(result, completionListener);
    }

    public static void userExists(String uid, final UserExistsInterface userExistsInterface) {
        mRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userExistsInterface.userExist(true);
                } else {
                    userExistsInterface.userExist(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                userExistsInterface.userExist(false);
            }
        });
    }

    public static void getUserData(String uid, ValueEventListener valueEventListener) {

        mRef.child(uid).addListenerForSingleValueEvent(valueEventListener);
    }
}
