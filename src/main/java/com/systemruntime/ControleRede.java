package com.systemruntime;

public class ControleRede {
    public static void desativar(String adaptador) throws Exception {
        String comando = "powershell.exe -Command \"Disable-NetAdapter -Name '" + adaptador
                + "' -Confirm:$false\"";
        Runtime.getRuntime().exec(comando);
    }

    public static void ativar(String adaptador) throws Exception {
        String comando = "powershell.exe -Command \"Enable-NetAdapter -Name '" + adaptador
                + "' -Confirm:$false\"";
        Runtime.getRuntime().exec(comando);
    }
}
