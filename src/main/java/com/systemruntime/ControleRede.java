package com.systemruntime;

public class ControleRede {
    public static void desativar() throws Exception {
        executar("Get-NetAdapter -Physical | Disable-NetAdapter -Confirm:$false");
    }

    public static void ativar() throws Exception {
        executar("Get-NetAdapter -Physical | Enable-NetAdapter -Confirm:$false");
    }

    private static void executar(String script) throws Exception {
        ProcessBuilder processo = new ProcessBuilder("powershell.exe", "-NoProfile", "-Command", script);
        processo.inheritIO();

        int codigo = processo.start().waitFor();
        if (codigo != 0) {
            throw new Exception("O PowerShell falhou, código " + codigo);
        }
    }
}
