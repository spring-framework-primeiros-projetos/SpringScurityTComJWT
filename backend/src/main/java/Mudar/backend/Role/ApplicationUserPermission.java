package Mudar.backend.Role;

public enum ApplicationUserPermission {
    TRANSPORTADOR_READ("transportador:read"),
    TRANSPORTADOR_WRITE("transportador:write"),
    TRANSPORTADOR_UPDATE("transportador:update"),
    TRANSPORTADOR_DELETE("transportador:delete"),
    
    SOLICITANTE_READ("solicitante:read"),
    SOLICITANTE_WRITE("solicitante:write"),
    SOLICITANTE_UPDATE("solicitante:update"),
    SOLICITANTE_DELETE("solicitante:delete"),
    ;
    
    
    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
