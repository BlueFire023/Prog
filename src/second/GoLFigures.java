package second;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 17/06/2023
 */
public class GoLFigures {
    ArrayList<GoLPrefab> figures = new ArrayList<>();

    //Still lifes
    GoLPrefab block;
    GoLPrefab beehive;
    GoLPrefab loaf;
    GoLPrefab boat;
    GoLPrefab tub;

    //Oscillators
    GoLPrefab blinker;
    GoLPrefab toad;
    GoLPrefab beacon;
    GoLPrefab pulsar;
    GoLPrefab pentadecathlon;

    //Spaceships
    GoLPrefab glider;
    GoLPrefab LWSS;
    GoLPrefab MWSS;
    GoLPrefab HWSS;

    //Methuselahs
    GoLPrefab rpentomino;
    GoLPrefab diehard;
    GoLPrefab acorn;

    //Glider guns
    GoLPrefab gosperGG;
    GoLPrefab simkinGG;

    //Indefinites
    GoLPrefab infinite_1;
    GoLPrefab infinite_2;
    GoLPrefab infinite_3;

    public GoLFigures() {
        Set<Point> blockSet = new HashSet<>();
        blockSet.add(new Point(0, 0));
        blockSet.add(new Point(0, 1));
        blockSet.add(new Point(1, 0));
        blockSet.add(new Point(1, 1));
        block = new GoLPrefab("Block", blockSet);
        figures.add(block);

        Set<Point> beehiveSet = new HashSet<>();
        beehiveSet.add(new Point(0, 1));
        beehiveSet.add(new Point(1, 0));
        beehiveSet.add(new Point(2, 0));
        beehiveSet.add(new Point(1, 2));
        beehiveSet.add(new Point(2, 2));
        beehiveSet.add(new Point(3, 1));
        beehive = new GoLPrefab("Beehive", beehiveSet);
        figures.add(beehive);

        Set<Point> loafSet = new HashSet<>();
        loafSet.add(new Point(1, 0));
        loafSet.add(new Point(2, 0));
        loafSet.add(new Point(0, 1));
        loafSet.add(new Point(1, 2));
        loafSet.add(new Point(2, 3));
        loafSet.add(new Point(3, 1));
        loafSet.add(new Point(3, 2));
        loaf = new GoLPrefab("Loaf", loafSet);
        figures.add(loaf);

        Set<Point> boatSet = new HashSet<>();
        boatSet.add(new Point(0, 1));
        boatSet.add(new Point(1, 0));
        boatSet.add(new Point(0, 0));
        boatSet.add(new Point(2, 1));
        boatSet.add(new Point(1, 2));
        boat = new GoLPrefab("Boat", boatSet);
        figures.add(boat);

        Set<Point> tubSet = new HashSet<>();
        tubSet.add(new Point(1, 0));
        tubSet.add(new Point(1, 2));
        tubSet.add(new Point(0, 1));
        tubSet.add(new Point(2, 1));
        tub = new GoLPrefab("Tub", tubSet);
        figures.add(tub);

        Set<Point> blinkerSet = new HashSet<>();
        blinkerSet.add(new Point(0, 0));
        blinkerSet.add(new Point(1, 0));
        blinkerSet.add(new Point(2, 0));
        blinker = new GoLPrefab("Blinker", blinkerSet);
        figures.add(blinker);

        Set<Point> toadSet = new HashSet<>();
        toadSet.add(new Point(1, 0));
        toadSet.add(new Point(2, 0));
        toadSet.add(new Point(3, 0));
        toadSet.add(new Point(0, 1));
        toadSet.add(new Point(1, 1));
        toadSet.add(new Point(2, 1));
        toad = new GoLPrefab("Toad", toadSet);
        figures.add(toad);

        Set<Point> beaconSet = new HashSet<>();
        beaconSet.add(new Point(0, 0));
        beaconSet.add(new Point(1, 0));
        beaconSet.add(new Point(0, 1));
        beaconSet.add(new Point(3, 2));
        beaconSet.add(new Point(3, 3));
        beaconSet.add(new Point(2, 3));
        beacon = new GoLPrefab("Beacon", beaconSet);
        figures.add(beacon);

        Set<Point> pulsarSet = new HashSet<>();
        pulsarSet.add(new Point(2, 0));
        pulsarSet.add(new Point(10, 7));
        pulsarSet.add(new Point(0, 2));
        pulsarSet.add(new Point(7, 10));
        pulsarSet.add(new Point(7, 9));
        pulsarSet.add(new Point(8, 7));
        pulsarSet.add(new Point(9, 7));
        pulsarSet.add(new Point(3, 0));
        pulsarSet.add(new Point(10, 5));
        pulsarSet.add(new Point(12, 8));
        pulsarSet.add(new Point(5, 10));
        pulsarSet.add(new Point(4, 12));
        pulsarSet.add(new Point(5, 9));
        pulsarSet.add(new Point(8, 5));
        pulsarSet.add(new Point(9, 5));
        pulsarSet.add(new Point(4, 0));
        pulsarSet.add(new Point(12, 3));
        pulsarSet.add(new Point(3, 12));
        pulsarSet.add(new Point(5, 2));
        pulsarSet.add(new Point(12, 4));
        pulsarSet.add(new Point(2, 12));
        pulsarSet.add(new Point(0, 10));
        pulsarSet.add(new Point(7, 2));
        pulsarSet.add(new Point(0, 9));
        pulsarSet.add(new Point(8, 0));
        pulsarSet.add(new Point(0, 8));
        pulsarSet.add(new Point(9, 0));
        pulsarSet.add(new Point(10, 0));
        pulsarSet.add(new Point(3, 5));
        pulsarSet.add(new Point(5, 4));
        pulsarSet.add(new Point(7, 3));
        pulsarSet.add(new Point(2, 7));
        pulsarSet.add(new Point(12, 2));
        pulsarSet.add(new Point(3, 7));
        pulsarSet.add(new Point(5, 3));
        pulsarSet.add(new Point(7, 4));
        pulsarSet.add(new Point(2, 5));
        pulsarSet.add(new Point(0, 4));
        pulsarSet.add(new Point(5, 8));
        pulsarSet.add(new Point(4, 7));
        pulsarSet.add(new Point(12, 10));
        pulsarSet.add(new Point(12, 9));
        pulsarSet.add(new Point(0, 3));
        pulsarSet.add(new Point(8, 12));
        pulsarSet.add(new Point(9, 12));
        pulsarSet.add(new Point(7, 8));
        pulsarSet.add(new Point(4, 5));
        pulsarSet.add(new Point(10, 12));
        pulsar = new GoLPrefab("Pulsar", pulsarSet);
        figures.add(pulsar);

        Set<Point> pentadecathlonSet = new HashSet<>();
        pentadecathlonSet.add(new Point(0, 2));
        pentadecathlonSet.add(new Point(1, 0));
        pentadecathlonSet.add(new Point(1, 1));
        pentadecathlonSet.add(new Point(1, 3));
        pentadecathlonSet.add(new Point(1, 4));
        pentadecathlonSet.add(new Point(1, 5));
        pentadecathlonSet.add(new Point(1, 6));
        pentadecathlonSet.add(new Point(1, 8));
        pentadecathlonSet.add(new Point(1, 9));
        pentadecathlonSet.add(new Point(0, 7));
        pentadecathlonSet.add(new Point(2, 2));
        pentadecathlonSet.add(new Point(2, 7));
        pentadecathlon = new GoLPrefab("Pentadecathlon", pentadecathlonSet);
        figures.add(pentadecathlon);

        Set<Point> gliderSet = new HashSet<>();
        gliderSet.add(new Point(0, 0));
        gliderSet.add(new Point(1, 1));
        gliderSet.add(new Point(2, 1));
        gliderSet.add(new Point(0, 2));
        gliderSet.add(new Point(1, 2));
        glider = new GoLPrefab("Glider", gliderSet);
        figures.add(glider);

        Set<Point> LWSSSet = new HashSet<>();
        LWSSSet.add(new Point(0, 0));
        LWSSSet.add(new Point(3, 0));
        LWSSSet.add(new Point(4, 1));
        LWSSSet.add(new Point(0, 2));
        LWSSSet.add(new Point(4, 2));
        LWSSSet.add(new Point(1, 3));
        LWSSSet.add(new Point(2, 3));
        LWSSSet.add(new Point(3, 3));
        LWSSSet.add(new Point(4, 3));
        LWSS = new GoLPrefab("LWSS", LWSSSet);
        figures.add(LWSS);

        Set<Point> MWSSSet = new HashSet<>();
        MWSSSet.add(new Point(3, 0));
        MWSSSet.add(new Point(4, 0));
        MWSSSet.add(new Point(0, 1));
        MWSSSet.add(new Point(1, 1));
        MWSSSet.add(new Point(2, 1));
        MWSSSet.add(new Point(4, 1));
        MWSSSet.add(new Point(5, 1));
        MWSSSet.add(new Point(0, 2));
        MWSSSet.add(new Point(1, 2));
        MWSSSet.add(new Point(2, 2));
        MWSSSet.add(new Point(3, 2));
        MWSSSet.add(new Point(4, 2));
        MWSSSet.add(new Point(1, 3));
        MWSSSet.add(new Point(2, 3));
        MWSSSet.add(new Point(3, 3));
        MWSS = new GoLPrefab("MWSS", MWSSSet);
        figures.add(MWSS);

        Set<Point> HWSSSet = new HashSet<>();
        HWSSSet.add(new Point(4, 4));
        HWSSSet.add(new Point(6, 3));
        HWSSSet.add(new Point(2, 0));
        HWSSSet.add(new Point(1, 4));
        HWSSSet.add(new Point(5, 4));
        HWSSSet.add(new Point(5, 1));
        HWSSSet.add(new Point(6, 4));
        HWSSSet.add(new Point(3, 0));
        HWSSSet.add(new Point(0, 1));
        HWSSSet.add(new Point(2, 4));
        HWSSSet.add(new Point(3, 4));
        HWSSSet.add(new Point(6, 2));
        HWSSSet.add(new Point(0, 3));
        HWSS = new GoLPrefab("HWSS", HWSSSet);
        figures.add(HWSS);

        Set<Point> rpentominoSet = new HashSet<>();
        rpentominoSet.add(new Point(0, 1));
        rpentominoSet.add(new Point(2, 0));
        rpentominoSet.add(new Point(1, 0));
        rpentominoSet.add(new Point(1, 1));
        rpentominoSet.add(new Point(1, 2));
        rpentomino = new GoLPrefab("R-Pentomino", rpentominoSet);
        figures.add(rpentomino);

        Set<Point> diehardSet = new HashSet<>();
        diehardSet.add(new Point(0, 1));
        diehardSet.add(new Point(1, 2));
        diehardSet.add(new Point(1, 1));
        diehardSet.add(new Point(6, 0));
        diehardSet.add(new Point(5, 2));
        diehardSet.add(new Point(6, 2));
        diehardSet.add(new Point(7, 2));
        diehard = new GoLPrefab("Diehard", diehardSet);
        figures.add(diehard);

        Set<Point> acornSet = new HashSet<>();
        acornSet.add(new Point(1, 2));
        acornSet.add(new Point(2, 2));
        acornSet.add(new Point(2, 0));
        acornSet.add(new Point(4, 1));
        acornSet.add(new Point(5, 2));
        acornSet.add(new Point(6, 2));
        acornSet.add(new Point(7, 2));
        acorn = new GoLPrefab("Acorn", acornSet);
        figures.add(acorn);

        Set<Point> gopserGGSet = new HashSet<>();
        gopserGGSet.add(new Point(24, 0));
        gopserGGSet.add(new Point(22, 1));
        gopserGGSet.add(new Point(24, 1));
        gopserGGSet.add(new Point(12, 2));

        gopserGGSet.add(new Point(13, 2));
        gopserGGSet.add(new Point(20, 2));
        gopserGGSet.add(new Point(21, 2));
        gopserGGSet.add(new Point(34, 2));
        gopserGGSet.add(new Point(35, 2));
        gopserGGSet.add(new Point(11, 3));

        gopserGGSet.add(new Point(15, 3));
        gopserGGSet.add(new Point(20, 3));
        gopserGGSet.add(new Point(21, 3));
        gopserGGSet.add(new Point(34, 3));
        gopserGGSet.add(new Point(35, 3));
        gopserGGSet.add(new Point(0, 4));
        gopserGGSet.add(new Point(1, 4));
        gopserGGSet.add(new Point(10, 4));
        gopserGGSet.add(new Point(16, 4));
        gopserGGSet.add(new Point(20, 4));
        gopserGGSet.add(new Point(21, 4));
        gopserGGSet.add(new Point(0, 5));
        gopserGGSet.add(new Point(1, 5));
        gopserGGSet.add(new Point(10, 5));
        gopserGGSet.add(new Point(14, 5));
        gopserGGSet.add(new Point(16, 5));
        gopserGGSet.add(new Point(17, 5));
        gopserGGSet.add(new Point(22, 5));
        gopserGGSet.add(new Point(24, 5));
        gopserGGSet.add(new Point(10, 6));
        gopserGGSet.add(new Point(16, 6));
        gopserGGSet.add(new Point(24, 6));
        gopserGGSet.add(new Point(11, 7));
        gopserGGSet.add(new Point(15, 7));
        gopserGGSet.add(new Point(12, 8));
        gopserGGSet.add(new Point(13, 8));


        gosperGG = new GoLPrefab("Gosper Glider Gun", gopserGGSet);
        figures.add(gosperGG);

        Set<Point> simkinGGSet = new HashSet<>();
        simkinGGSet.add(new Point(0, 0));
        simkinGGSet.add(new Point(0, 1));
        simkinGGSet.add(new Point(1, 0));
        simkinGGSet.add(new Point(1, 1));
        simkinGGSet.add(new Point(7, 0));
        simkinGGSet.add(new Point(8, 0));
        simkinGGSet.add(new Point(7, 1));
        simkinGGSet.add(new Point(8, 1));
        simkinGGSet.add(new Point(4, 3));
        simkinGGSet.add(new Point(5, 3));
        simkinGGSet.add(new Point(4, 4));
        simkinGGSet.add(new Point(5, 4));
        simkinGGSet.add(new Point(31,11));
        simkinGGSet.add(new Point(31,12));
        simkinGGSet.add(new Point(32,11));
        simkinGGSet.add(new Point(0,0));
        simkinGG = new GoLPrefab("Simkin Glider Gun", simkinGGSet);
        figures.add(simkinGG);

        Set<Point> infinite_1Set = new HashSet<>();
        infinite_1Set.add(new Point(0, 5));
        infinite_1Set.add(new Point(6, 0));
        infinite_1Set.add(new Point(2, 4));
        infinite_1Set.add(new Point(2, 5));
        infinite_1Set.add(new Point(4, 1));
        infinite_1Set.add(new Point(4, 2));
        infinite_1Set.add(new Point(4, 3));
        infinite_1Set.add(new Point(6, 1));
        infinite_1Set.add(new Point(6, 2));
        infinite_1Set.add(new Point(7, 1));
        infinite_1 = new GoLPrefab("Infinite 1", infinite_1Set);
        figures.add(infinite_1);

        Set<Point> infinite_2Set = new HashSet<>();
        infinite_2Set.add(new Point(0, 0));
        infinite_2Set.add(new Point(1, 0));
        infinite_2Set.add(new Point(2, 0));
        infinite_2Set.add(new Point(4, 0));
        infinite_2Set.add(new Point(0, 1));
        infinite_2Set.add(new Point(3, 2));
        infinite_2Set.add(new Point(4, 2));
        infinite_2Set.add(new Point(1, 3));
        infinite_2Set.add(new Point(2, 3));
        infinite_2Set.add(new Point(4, 3));
        infinite_2Set.add(new Point(0, 4));
        infinite_2Set.add(new Point(2, 4));
        infinite_2Set.add(new Point(4, 4));
        infinite_2 = new GoLPrefab("Infinite 2", infinite_2Set);
        figures.add(infinite_2);

        Set<Point> infinite_3Set = new HashSet<>();
        infinite_3Set.add(new Point(0, 0));
        infinite_3Set.add(new Point(1, 0));
        infinite_3Set.add(new Point(2, 0));
        infinite_3Set.add(new Point(3, 0));
        infinite_3Set.add(new Point(4, 0));
        infinite_3Set.add(new Point(5, 0));
        infinite_3Set.add(new Point(6, 0));
        infinite_3Set.add(new Point(7, 0));
        infinite_3Set.add(new Point(9, 0));
        infinite_3Set.add(new Point(10, 0));
        infinite_3Set.add(new Point(11, 0));
        infinite_3Set.add(new Point(12, 0));
        infinite_3Set.add(new Point(13, 0));
        infinite_3Set.add(new Point(17, 0));
        infinite_3Set.add(new Point(18, 0));
        infinite_3Set.add(new Point(19, 0));
        infinite_3Set.add(new Point(27, 0));
        infinite_3Set.add(new Point(28, 0));
        infinite_3Set.add(new Point(29, 0));
        infinite_3Set.add(new Point(30, 0));
        infinite_3Set.add(new Point(31, 0));
        infinite_3Set.add(new Point(32, 0));
        infinite_3Set.add(new Point(33, 0));
        infinite_3Set.add(new Point(35, 0));
        infinite_3Set.add(new Point(36, 0));
        infinite_3Set.add(new Point(37, 0));
        infinite_3Set.add(new Point(38, 0));
        infinite_3Set.add(new Point(39, 0));
        infinite_3 = new GoLPrefab("Infinite 3", infinite_3Set);
        figures.add(infinite_3);
    }

    public GoLPrefab getFigure(int position) {
        return figures.get(position);
    }
}
