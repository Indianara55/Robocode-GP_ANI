package gp_ant_nic_ind;

import robocode.*;
import java.awt.Color;

public class GP_ANI_Entrega extends Robot { 

    public void run() {
        setBodyColor(Color.DARK_GRAY);
        setRadarColor(Color.RED);

        // 1. Alinhamento inicial
        turnLeft(getHeading() % 90); 
        ahead(5000); 
        turnRight(90);

        // O Pulo do Gato (Canhão Fixo): Vira a arma para a direita UMA SÓ VEZ.
        // Como o chassi vai contornar a parede pela esquerda, a arma ficará 100% 
        // do tempo apontada para o "miolo" da arena, escaneando tudo sem usar motor.
        turnGunRight(90);

        // Laço de Repetição infinito (Enquanto)
        while (true) {
            // A melhor defesa na 1ª fase é NUNCA FREAR. 
            // Ele vai voar pelo perímetro ininterruptamente.
            ahead(5000); 
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // Se achou alguém, é porque o alvo cruzou a linha do canhão fixo.
        // Atira imediatamente com o robô em movimento (drive-by).
        if (e.getDistance() < 200) {
            fire(3.0); 
        } else {
            fire(1.0); 
        }
    }

    public void onHitWall(HitWallEvent e) {
        // Nas quinas, o chassi vira 90 graus. A arma (que é atrelada ao chassi) 
        // vira junto automaticamente, continuando a apontar para o centro na nova parede.
        turnRight(90);
    }
}
