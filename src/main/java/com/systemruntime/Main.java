package com.systemruntime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) throws Exception {
        File arquivo = Configuracao.ARQUIVO;

        if (args.length == 0) {
            if (!arquivo.exists()) {
                Configuracao configuracao = new Configuracao();
                configuracao.configurarHorario(arquivo);
            }
            // LocalTime horarioConfiguracao = LocalTime.parse(horarioDigitado);
            // ControleRede.ativar("Wi-Fi");

            // MonitorHorario monitor = new MonitorHorario();
            // monitor.iniciar(horarioConfiguracao);
            TarefaWindows.configurar();

            String caminhoPrograma = ProcessHandle.current().info().command().orElseThrow();
            ProcessBuilder processo = new ProcessBuilder(caminhoPrograma, "--background");
            processo.start();
            return;
        }
        // Modo background
        if (!ProcessoUnico.iniciar()) {
            System.out.println("O processo já está em execução.");
            return;
        }

        BufferedReader leitura = new BufferedReader(new FileReader(arquivo));
        String horarioTxt = leitura.readLine();
        leitura.close();

        LocalTime horarioConfigurado = LocalTime.parse(horarioTxt);
        try {
            ControleRede.ativar();
        } catch (Exception e) {
            System.out.println("Não foi possível ativar a rede!" + e.getMessage());
        }

        MonitorHorario monitor = new MonitorHorario();
        monitor.iniciar(horarioConfigurado);

    }
}