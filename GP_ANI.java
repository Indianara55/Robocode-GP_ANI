package gp_ant_nic_ind;

import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

public class GP_ANI extends AdvancedRobot {

    public void run() {
        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true); // Linha corrigida
        
        setBodyColor(Color.DARK_GRAY);
        setRadarColor(Color.RED);

        irParaParedeMaisProxima();

        while (true) {
            setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
            setAhead(10000);
            execute();
        }
    }

    private void irParaParedeMaisProxima() {
        double x = getX();
        double y = getY();
        double altura = getBattleFieldHeight();
        double largura = getBattleFieldWidth();

        double topo = altura - y;
        double baixo = y;
        double direita = largura - x;
        double esquerda = x;

        double minDist = Math.min(Math.min(topo, baixo), Math.min(esquerda, direita));
        double anguloAlvo;

        if (minDist == topo) anguloAlvo = 0;
        else if (minDist == direita) anguloAlvo = 90;
        else if (minDist == baixo) anguloAlvo = 180;
        else anguloAlvo = 270;

        turnRight(Utils.normalRelativeAngleDegrees(anguloAlvo - getHeading()));
        ahead(minDist - 18);
        turnRight(90);
    }

    public void onHitWall(HitWallEvent e) {
        turnRight(90);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double anguloAbsoluto = getHeadingRadians() + e.getBearingRadians();
        
        setTurnRadarRightRadians(Utils.normalRelativeAngle(anguloAbsoluto - getRadarHeadingRadians()));
        setTurnGunRightRadians(Utils.normalRelativeAngle(anguloAbsoluto - getGunHeadingRadians()));

        double forcaTiro = Math.min(3.0, 400.0 / Math.max(e.getDistance(), 1));

        if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 5) {
            setFire(forcaTiro);
        }
    }
}