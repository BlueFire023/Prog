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
    private final ArrayList<GoLPrefab> figures = new ArrayList<>(); //Erstellt eine Arraylist "GoLPrefab, um alle Figuren als "Prefabs" darin zu speichern
    private final int stillLifesCount = 8, oscillatorsCount = 9, spaceshipsCount = 4, methuselahsCount = 3, ggCount = 2, otherCount = 4;

    public GoLFigures() {
        //Erstellt die Figur "Block" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> blockSet = new HashSet<>();
        blockSet.add(new Point(0, 0));
        blockSet.add(new Point(0, 1));
        blockSet.add(new Point(1, 0));
        blockSet.add(new Point(1, 1));
        figures.add(new GoLPrefab("Block", blockSet));

        //Erstellt die Figur "Beehive" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> beehiveSet = new HashSet<>();
        beehiveSet.add(new Point(0, 1));
        beehiveSet.add(new Point(1, 0));
        beehiveSet.add(new Point(2, 0));
        beehiveSet.add(new Point(1, 2));
        beehiveSet.add(new Point(2, 2));
        beehiveSet.add(new Point(3, 1));
        figures.add(new GoLPrefab("Beehive", beehiveSet));

        //Erstellt die Figur "Loaf" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> loafSet = new HashSet<>();
        loafSet.add(new Point(1, 0));
        loafSet.add(new Point(2, 0));
        loafSet.add(new Point(0, 1));
        loafSet.add(new Point(1, 2));
        loafSet.add(new Point(2, 3));
        loafSet.add(new Point(3, 1));
        loafSet.add(new Point(3, 2));
        figures.add(new GoLPrefab("Loaf", loafSet));

        //Erstellt die Figur "Boat" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> boatSet = new HashSet<>();
        boatSet.add(new Point(0, 1));
        boatSet.add(new Point(1, 0));
        boatSet.add(new Point(0, 0));
        boatSet.add(new Point(2, 1));
        boatSet.add(new Point(1, 2));
        figures.add(new GoLPrefab("Boat", boatSet));

        //Erstellt die Figur "Tub" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> tubSet = new HashSet<>();
        tubSet.add(new Point(1, 0));
        tubSet.add(new Point(1, 2));
        tubSet.add(new Point(0, 1));
        tubSet.add(new Point(2, 1));
        figures.add(new GoLPrefab("Tub", tubSet));

        //Erstellt die Figur "Barge" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> bargeSet = new HashSet<>();
        bargeSet.add(new Point(2, 0));
        bargeSet.add(new Point(1, 1));
        bargeSet.add(new Point(0, 2));
        bargeSet.add(new Point(3, 1));
        bargeSet.add(new Point(2, 2));
        bargeSet.add(new Point(1, 3));
        figures.add(new GoLPrefab("Barge", bargeSet));

        //Erstellt die Figur "Pond" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> pondSet = new HashSet<>();
        pondSet.add(new Point(1, 0));
        pondSet.add(new Point(2, 0));
        pondSet.add(new Point(1, 3));
        pondSet.add(new Point(2, 3));
        pondSet.add(new Point(0, 1));
        pondSet.add(new Point(0, 2));
        pondSet.add(new Point(3, 1));
        pondSet.add(new Point(3, 2));
        figures.add(new GoLPrefab("Pond", pondSet));

        //Erstellt die Figur "Eater" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> eaterSet = new HashSet<>();
        eaterSet.add(new Point(0, 0));
        eaterSet.add(new Point(1, 0));
        eaterSet.add(new Point(0, 1));
        eaterSet.add(new Point(2, 1));
        eaterSet.add(new Point(2, 2));
        eaterSet.add(new Point(2, 3));
        eaterSet.add(new Point(3, 3));
        figures.add(new GoLPrefab("Eater", eaterSet));

        //Erstellt die Figur "Blinker" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> blinkerSet = new HashSet<>();
        blinkerSet.add(new Point(0, 0));
        blinkerSet.add(new Point(1, 0));
        blinkerSet.add(new Point(2, 0));
        figures.add(new GoLPrefab("Blinker", blinkerSet));

        //Erstellt die Figur "Clock" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> clockSet = new HashSet<>();
        clockSet.add(new Point(0, 1));
        clockSet.add(new Point(1, 1));
        clockSet.add(new Point(2, 0));
        clockSet.add(new Point(2, 2));
        clockSet.add(new Point(3, 2));
        clockSet.add(new Point(1, 3));
        figures.add(new GoLPrefab("Clock", clockSet));

        //Erstellt die Figur "Toad" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> toadSet = new HashSet<>();
        toadSet.add(new Point(1, 0));
        toadSet.add(new Point(2, 0));
        toadSet.add(new Point(3, 0));
        toadSet.add(new Point(0, 1));
        toadSet.add(new Point(1, 1));
        toadSet.add(new Point(2, 1));
        figures.add(new GoLPrefab("Toad", toadSet));

        //Erstellt die Figur "Beacon" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> beaconSet = new HashSet<>();
        beaconSet.add(new Point(0, 0));
        beaconSet.add(new Point(1, 0));
        beaconSet.add(new Point(0, 1));
        beaconSet.add(new Point(3, 2));
        beaconSet.add(new Point(3, 3));
        beaconSet.add(new Point(2, 3));
        figures.add(new GoLPrefab("Beacon", beaconSet));

        //Erstellt die Figur "Tripole" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> tripoleSet = new HashSet<>();
        tripoleSet.add(new Point(0, 0));
        tripoleSet.add(new Point(1, 0));
        tripoleSet.add(new Point(0, 1));
        tripoleSet.add(new Point(1, 2));
        tripoleSet.add(new Point(3, 2));
        tripoleSet.add(new Point(4, 3));
        tripoleSet.add(new Point(3, 4));
        tripoleSet.add(new Point(4, 4));
        figures.add(new GoLPrefab("Tripole", tripoleSet));

        //Erstellt die Figur "Octagon" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> octagonSet = new HashSet<>();
        octagonSet.add(new Point(3, 0));
        octagonSet.add(new Point(4, 0));
        octagonSet.add(new Point(2, 1));
        octagonSet.add(new Point(5, 1));
        octagonSet.add(new Point(1, 2));
        octagonSet.add(new Point(6, 2));
        octagonSet.add(new Point(0, 3));
        octagonSet.add(new Point(7, 3));
        octagonSet.add(new Point(0, 4));
        octagonSet.add(new Point(7, 4));
        octagonSet.add(new Point(1, 5));
        octagonSet.add(new Point(6, 5));
        octagonSet.add(new Point(2, 6));
        octagonSet.add(new Point(5, 6));
        octagonSet.add(new Point(3, 7));
        octagonSet.add(new Point(4, 7));
        figures.add(new GoLPrefab("Octagon", octagonSet));

        //Erstellt die Figur "Pulsar" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        figures.add(new GoLPrefab("Pulsar", pulsarSet));

        //Erstellt die Figur "Pentadecathlon" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        figures.add(new GoLPrefab("Pentadecathlon", pentadecathlonSet));

        //Erstellt die Figur "Fontaine" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> fontaineSet = new HashSet<>();
        fontaineSet.add(new Point(2, 0));
        fontaineSet.add(new Point(3, 0));
        fontaineSet.add(new Point(5, 0));
        fontaineSet.add(new Point(6, 0));
        fontaineSet.add(new Point(3, 2));
        fontaineSet.add(new Point(3, 3));
        fontaineSet.add(new Point(3, 4));
        fontaineSet.add(new Point(5, 2));
        fontaineSet.add(new Point(5, 3));
        fontaineSet.add(new Point(5, 4));
        fontaineSet.add(new Point(0, 4));
        fontaineSet.add(new Point(1, 4));
        fontaineSet.add(new Point(1, 5));
        fontaineSet.add(new Point(2, 5));
        fontaineSet.add(new Point(8, 4));
        fontaineSet.add(new Point(7, 4));
        fontaineSet.add(new Point(7, 5));
        fontaineSet.add(new Point(6, 5));
        figures.add(new GoLPrefab("Fontaine", fontaineSet));

        //Erstellt die Figur "Glider" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> gliderSet = new HashSet<>();
        gliderSet.add(new Point(0, 0));
        gliderSet.add(new Point(1, 1));
        gliderSet.add(new Point(2, 1));
        gliderSet.add(new Point(0, 2));
        gliderSet.add(new Point(1, 2));
        figures.add(new GoLPrefab("Glider", gliderSet));

        //Erstellt die Figur "LWSS" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        figures.add(new GoLPrefab("LWSS", LWSSSet));

        //Erstellt die Figur "MWSS" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        figures.add(new GoLPrefab("MWSS", MWSSSet));

        //Erstellt die Figur "HWSS" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        figures.add(new GoLPrefab("HWSS", HWSSSet));

        //Erstellt die Figur "R-Pentomino" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> rpentominoSet = new HashSet<>();
        rpentominoSet.add(new Point(0, 1));
        rpentominoSet.add(new Point(2, 0));
        rpentominoSet.add(new Point(1, 0));
        rpentominoSet.add(new Point(1, 1));
        rpentominoSet.add(new Point(1, 2));
        figures.add(new GoLPrefab("R-Pentomino", rpentominoSet));

        //Erstellt die Figur "Diehard" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> diehardSet = new HashSet<>();
        diehardSet.add(new Point(0, 1));
        diehardSet.add(new Point(1, 2));
        diehardSet.add(new Point(1, 1));
        diehardSet.add(new Point(6, 0));
        diehardSet.add(new Point(5, 2));
        diehardSet.add(new Point(6, 2));
        diehardSet.add(new Point(7, 2));
        figures.add(new GoLPrefab("Diehard", diehardSet));

        //Erstellt die Figur "Acorn" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> acornSet = new HashSet<>();
        acornSet.add(new Point(1, 2));
        acornSet.add(new Point(2, 2));
        acornSet.add(new Point(2, 0));
        acornSet.add(new Point(4, 1));
        acornSet.add(new Point(5, 2));
        acornSet.add(new Point(6, 2));
        acornSet.add(new Point(7, 2));
        figures.add(new GoLPrefab("Acorn", acornSet));

        //Erstellt die Figur "Gosper Glider Gun" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        figures.add(new GoLPrefab("Gosper Glider Gun", gopserGGSet));

        //Erstellt die Figur "Simkin Glider Gun" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        simkinGGSet.add(new Point(22, 9));
        simkinGGSet.add(new Point(22, 12));
        simkinGGSet.add(new Point(23, 12));
        simkinGGSet.add(new Point(25, 9));
        simkinGGSet.add(new Point(23, 9));
        simkinGGSet.add(new Point(26, 9));
        simkinGGSet.add(new Point(26, 13));
        simkinGGSet.add(new Point(21, 10));
        simkinGGSet.add(new Point(27, 10));
        simkinGGSet.add(new Point(27, 12));
        simkinGGSet.add(new Point(21, 11));
        simkinGGSet.add(new Point(21, 12));
        simkinGGSet.add(new Point(28, 11));
        simkinGGSet.add(new Point(31, 11));
        simkinGGSet.add(new Point(31, 12));
        simkinGGSet.add(new Point(32, 11));
        simkinGGSet.add(new Point(32, 12));
        simkinGGSet.add(new Point(23, 19));
        simkinGGSet.add(new Point(22, 19));
        simkinGGSet.add(new Point(21, 19));
        simkinGGSet.add(new Point(20, 18));
        simkinGGSet.add(new Point(20, 17));
        simkinGGSet.add(new Point(21, 17));
        simkinGGSet.add(new Point(23, 20));
        figures.add(new GoLPrefab("Simkin Glider Gun", simkinGGSet));

        //Erstellt die Figur "Infinite 1" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        figures.add(new GoLPrefab("Infinite 1", infinite_1Set));

        //Erstellt die Figur "Infinite 2" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        figures.add(new GoLPrefab("Infinite 2", infinite_2Set));

        //Erstellt die Figur "Infinite 3" aus einem Set von Pixeln, und erstellt daraus ein Prefab
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
        infinite_3Set.add(new Point(26, 0));
        infinite_3Set.add(new Point(27, 0));
        infinite_3Set.add(new Point(28, 0));
        infinite_3Set.add(new Point(29, 0));
        infinite_3Set.add(new Point(30, 0));
        infinite_3Set.add(new Point(31, 0));
        infinite_3Set.add(new Point(32, 0));
        infinite_3Set.add(new Point(34, 0));
        infinite_3Set.add(new Point(35, 0));
        infinite_3Set.add(new Point(36, 0));
        infinite_3Set.add(new Point(37, 0));
        infinite_3Set.add(new Point(38, 0));
        figures.add(new GoLPrefab("Infinite 3", infinite_3Set));

        //Erstellt die Figur "U" aus einem Set von Pixeln, und erstellt daraus ein Prefab
        Set<Point> USet = new HashSet<>();
        USet.add(new Point(0, 0));
        USet.add(new Point(0, 1));
        USet.add(new Point(0, 2));
        USet.add(new Point(0, 4));
        USet.add(new Point(0, 5));
        USet.add(new Point(0, 6));
        USet.add(new Point(1, 6));
        USet.add(new Point(1, 0));
        USet.add(new Point(2, 0));
        USet.add(new Point(2, 6));
        USet.add(new Point(2, 5));
        USet.add(new Point(2, 4));
        USet.add(new Point(2, 2));
        USet.add(new Point(2, 1));
        figures.add(new GoLPrefab("U", USet));
    }

    public GoLPrefab getFigure(int position) { //Gibt die Figur and der angegebenen Position zurück
        return figures.get(position);
    }
}
