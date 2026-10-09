package com.systemruntime;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitorHorario {
    private ScheduledExecutorService scheduler;
    private boolean redeDesativada = false;
    private LocalTime horarioBase;
    private long tempoBase;
    private static final LocalTime FIM = LocalTime.of(07, 57);
    private LocalTime inicio;

    public void iniciar(LocalTime horarioDesejado) {
        this.inicio = horarioDesejado;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Verificando o horario...");

            if (!redeDesativada) {
                try {
                    LocalTime horarioAtual = ObterHorario.obterHorarioComTentativas(12, 5);
                    if (horarioAtual == null) {
                        return;
                    }
                    System.out.println("Horário atual: " + horarioAtual);

                    horarioBase = horarioAtual;
                    tempoBase = System.nanoTime();
                    if (dentroDaJanela(horarioAtual)) {
                        ControleRede.desativar();
                        System.out.println("Desativou");
                        redeDesativada = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    long tempoPassado = System.nanoTime() - tempoBase;
                    long segundosPassados = TimeUnit.NANOSECONDS.toSeconds(tempoPassado);
                    LocalTime horarioAtual = horarioBase.plusSeconds(segundosPassados);
                    System.out.println("Horário estimado: " + horarioAtual);
                    if (!dentroDaJanela(horarioAtual)) {
                        ControleRede.ativar();
                        System.out.println("Ativou");
                        redeDesativada = false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private boolean dentroDaJanela(LocalTime agora) {
        if (inicio.isBefore(FIM)) {
            return !agora.isBefore(inicio) && agora.isBefore(FIM);
        }
        return !agora.isBefore(inicio) || agora.isBefore(FIM);
    }

}