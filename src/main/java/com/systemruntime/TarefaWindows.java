package com.systemruntime;

public class TarefaWindows {
    public static void configurar() throws Exception {
        String caminho = ProcessHandle.current().info().command().orElseThrow();

        String comando = "schtasks /Create"
                + " /TN \"System Runtime\""
                + " /TR \"\\\"" + caminho + "\\\" --background\""
                + " /SC ONSTART"
                + " /RU SYSTEM"
                + " /RL HIGHEST"
                + " /F";

        ProcessBuilder processo = new ProcessBuilder("cmd.exe", "/c", comando);
        processo.inheritIO();

        Process resultado = processo.start();
        int codigo = resultado.waitFor();

        if (codigo == 0) {
            System.out.println("Tarefa do windows configurada com sucesso!");

            String script = "$s = New-ScheduledTaskSettingsSet -AllowStartIfOnBatteries"
                    + " -DontStopIfGoingOnBatteries -ExecutionTimeLimit (New-TimeSpan -Seconds 0);"
                    + " Set-ScheduledTask -TaskName 'System Runtime' -Settings $s";

            ProcessBuilder ajuste = new ProcessBuilder("powershell.exe", "-NoProfile", "-Command", script);
            ajuste.inheritIO();
            int codigoAjuste = ajuste.start().waitFor();

            if (codigoAjuste == 0) {
                System.out.println("Limite de 72h e regra de bateria removidos.");
            } else {
                System.out.println("Não foi possível ajustar as configurações da tarefa.");
            }
        } else {
            System.out.println("Não foi possível configurar a tarefa.");
        }
    }
}
