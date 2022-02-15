package com.example.firebasetemplate;

import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class PostsLikeFragment extends PostsHomeFragment{
    @Override
    Query setQuery() {
        HashMap<String,Boolean> a = new HashMap<>();
        a.put(auth.getUid(),true);
        return db.collection("posts");
    }

}
