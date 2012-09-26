package com.larsbutler.gamedemo.math;

import static org.junit.Assert.*;

import java.awt.geom.Rectangle2D;

import org.junit.Test;

import com.larsbutler.gamedemo.models.Player;

public class CollisionTest {

    @Test
    public void testGetYPathBox() {
        Player p = new Player(0.0, 100.0, 48, 96);
        p.getPrevYState().p = 0.0;

        Rectangle2D.Double expected = new Rectangle2D.Double(
            0.0, 0.0, 48.0, 196.0);

        assertTrue(expected.equals(Collision.getYPathBox(p)));
    }

    @Test
    public void testGetYPathBox2() {
        Player p = new Player(10.0, 1.0, 50, 100);
        p.getPrevYState().p = 101.0;

        Rectangle2D.Double expected = new Rectangle2D.Double(
            10.0, 1.0, 50.0, 200.0);

        assertTrue(expected.equals(Collision.getYPathBox(p)));
    }

    @Test
    public void testGetXPathBox() {
        Player p  = new Player(100.0, 0.0, 48, 96);
        // the prev y state doesn't matter,
        // but the prev x state does
        p.getPrevXState().p = 0.0;

        Rectangle2D.Double expected = new Rectangle2D.Double(
            0.0, 0.0, 148.0, 96.0);

        assertTrue(expected.equals(Collision.getXPathBox(p)));
    }

    @Test
    public void testGetXPathBox2() {
        Player p = new Player(1.0, 10.0, 50, 100);
        // the prev y state doesn't matter,
        // but the prev x state does
        p.getPrevXState().p = 101.0;

        Rectangle2D.Double expected = new Rectangle2D.Double(
            1.0, 10.0, 150.0, 100.0);

        assertTrue(expected.equals(Collision.getXPathBox(p)));
    }

    @Test
    public void testGetYCorrectionFalling() {
        Player p = new Player(0.0, 70.0, 20, 20);
        p.getPrevYState().p = 0.0;
        p.getYState().v = 70.0;  // positive y velocity -> falling
        Rectangle2D.Double hitBox = new Rectangle2D.Double(
            10.0, 40.0, 60.0, 10.0);

        assertEquals(-50.0, Collision.getYCorrection(p, hitBox), 0.0);
    }

    @Test
    public void testGetYCorrectionRising() {
        Player p = new Player(0.0, 0.0, 20, 20);
        p.getPrevYState().p = 70.0;
        p.getYState().v = -70.0;  // negative y velocity -> rising
        Rectangle2D.Double hitBox = new Rectangle2D.Double(
            10.0, 40.0, 60.0, 10.0);

        assertEquals(50.0, Collision.getYCorrection(p, hitBox), 0.0);
    }

    @Test
    public void testGetYCorrectionNoIntersection() {
        Player p = new Player(0.0, 10.0, 20, 20);
        p.getPrevYState().p = 0.0;
        p.getYState().v = 10.0;

        Rectangle2D.Double hitBox = new Rectangle2D.Double(
            0.0, 30.0, 10.0, 10.0);
        assertEquals(0.0, Collision.getYCorrection(p, hitBox), 0.0);
    }

    @Test
    public void testGetXCorrectionMovingLeft() {
        Player p = new Player(0.0, 0.0, 20, 20);
        p.getPrevXState().p = 100.0;
        p.getXState().v = -100.0;

        Rectangle2D.Double hitBox = new Rectangle2D.Double(
            50.0, 0.0, 10.0, 10.0);
        assertEquals(60.0, Collision.getXCorrection(p, hitBox), 0.0);
    }

    @Test
    public void testGetXCorrectionMovingRight() {
        Player p = new Player(100.0, 0.0, 20, 20);
        p.getPrevXState().p = 0.0;
        p.getXState().v = 100.0;

        Rectangle2D.Double hitBox = new Rectangle2D.Double(
            50.0, 0.0, 10.0, 10.0);
        assertEquals(-70.0, Collision.getXCorrection(p, hitBox), 0.0);
    }

    @Test
    public void testGetXCorrectionNoIntersection() {
        Player p = new Player(10.0, 0.0, 20, 20);
        p.getPrevXState().p = 0.0;
        p.getXState().v = 10.0;

        Rectangle2D.Double hitBox = new Rectangle2D.Double(
            30.0, 0.0, 10.0, 10.0);
        assertEquals(0.0, Collision.getXCorrection(p, hitBox), 0.0);
    }
}

