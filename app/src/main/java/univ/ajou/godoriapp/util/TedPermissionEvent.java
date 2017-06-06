package univ.ajou.godoriapp.util;

import java.util.ArrayList;

public class TedPermissionEvent {

    public boolean permission;
    public ArrayList<String> deniedPermissions;


    public TedPermissionEvent(boolean permission, ArrayList<String> deniedPermissions
    ) {
        this.permission = permission;
        this.deniedPermissions = deniedPermissions;
    }



    public boolean hasPermission() {
        return permission;
    }


    public ArrayList<String> getDeniedPermissions() {
        return deniedPermissions;
    }
}
