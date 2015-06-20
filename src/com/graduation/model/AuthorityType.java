package com.graduation.model;

/**
 * Created by Stephen on 5/22/2014.
 */
public enum AuthorityType {

    //The order of declaration is very important, the value should be from small to large.
    USER_LIST("View user list", 0),
    USER_CREATE("Create user", 1),
    USER_UPDATE("Update user", 2),
    USER_DELETE("Delete user", 3),
    USER_INIT_PASSWORD("Init user password", 4),

    ROLE_LIST("View role list", 5),
    ROLE_CREATE("Create role", 6),
    ROLE_UPDATE("Update role", 7),
    ROLE_DELETE("Delete role", 8),

    GROUP_LIST("View group list", 9),
    GROUP_CREATE("Create group", 10),
    GROUP_UPDATE("Update group", 11),
    GROUP_DELETE("Delete group", 12),

    AUTHORITY_EDIT("Edit authority", 13),

    SQL_QUERY("SQL query", 14),

    FILE_UPLOAD("Upload file", 15),



    ;

    private String authorityName;
    private int index;

    AuthorityType(String authorityName, int index) {
        this.authorityName = authorityName;
        this.index = index;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getName(){
        return this.name();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static AuthorityType getAuthorityType(int index){
        for(AuthorityType authorityType : AuthorityType.values()){
            if(authorityType.getIndex() == index){
                return authorityType;
            }
        }

        return null;
    }
}
