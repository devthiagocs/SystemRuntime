// package com.systemruntime;

// public class TarefaWindows {
//     public static void configurar() throws Exception {

//         String caminho = ProcessHandle.current().info().command().orElseThrow();

//         String comando = "schtasks /Create"
//                 + " /TN \"System Runtime\""
//                 + " /TR \"\\\"" + caminho + "\\\" --background\""
//                 + "/SC ONSTART"
//                 + "/RL HIGHEST"
//                 + " /F";

//         ProcessBuilder processo = new ProcessBuilder("cmd.exe", "/c", comando);

//         processo.inheritIO();

//         Process resultado = processo.start();
//         int codigo = resultado.waitFor();

//         if (codigo == 0) {
//             System.out.println("Tarefa do windows configurada com sucesso!");
//         } else {
//             System.out.println("Não foi possível configurar a tarefa.");
//         }
//     }
// }
package com.systemruntime;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TarefaWindows {

    public static void configurar() throws Exception {

        String caminho = ProcessHandle.current()
                .info()
                .command()
                .orElseThrow();

        String comando = "schtasks /Create"
                + " /TN \"System Runtime\""
                + " /TR \"\\\"" + caminho + "\\\" --background\""
                + " /RU SYSTEM"
                + " /SC ONSTART"
                + " /RL HIGHEST"
                + " /F";

        System.out.println("Comando executado:");
        System.out.println(comando);

        ProcessBuilder processo = new ProcessBuilder(
                "cmd.exe",
                "/c",
                comando);

        Process resultado = processo.start();

        BufferedReader saida = new BufferedReader(
                new InputStreamReader(resultado.getInputStream()));

        BufferedReader erro = new BufferedReader(
                new InputStreamReader(resultado.getErrorStream()));

        String linha;

        System.out.println("\nSAÍDA DO WINDOWS:");

        while ((linha = saida.readLine()) != null) {
            System.out.println(linha);
        }

        System.out.println("\nERRO DO WINDOWS:");

        while ((linha = erro.readLine()) != null) {
            System.out.println(linha);
        }

        int codigo = resultado.waitFor();

        System.out.println("\nCódigo de saída: " + codigo);
    }

    public static void main(String[] args) throws Exception {
        configurar();
    }
}