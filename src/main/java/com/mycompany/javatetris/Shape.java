/*
 *Name: Arten Ruzaev
 */
package com.mycompany.javatetris; // project libary

import java.util.Random; // allows for random operations


public class Shape { // class initialization

    enum Tetrominoes { NoShape, ZShape, SShape, LineShape,  // sets up a special class for pieces (enum means that all of the pieces are constant or unchangable)
               TShape, SquareShape, LShape, MirroredLShape };

    private Tetrominoes pieceShape; // sets up variable for shapes
    private int coords[][];
    private int[][][] coordsTable;


    public Shape() {

        coords = new int[4][2]; // coordinates for the pieces
        setShape(Tetrominoes.NoShape); // sets up the shapes

    }

    public void setShape(Tetrominoes shape) {

         coordsTable = new int[][][] { // holds all the possible coordinates for the shapes
            { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
            { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
            { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
            { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
            { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
            { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
        };

         // this for loop puts one row of the coordinates into an array for a piece
        for (int i = 0; i < 4 ; i++) { 
            for (int j = 0; j < 2; ++j) {
                coords[i][j] = coordsTable[shape.ordinal()][i][j]; // ordinal means that the order of the enum above stays the same
            }
        }
        pieceShape = shape;

    }
    
    
    // sets up the coordinates in the array
    private void setX(int index, int x) { coords[index][0] = x; } 
    private void setY(int index, int y) { coords[index][1] = y; } 
    public int x(int index) { return coords[index][0]; }
    public int y(int index) { return coords[index][1]; }
    public Tetrominoes getShape()  { return pieceShape; } 

    // function for making random shapes
    public void setRandomShape()
    {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;
        Tetrominoes[] values = Tetrominoes.values(); 
        setShape(values[x]);
    }

    // the minimum coordinates for x
    public int minX()
    {
      int m = coords[0][0];
      for (int i=0; i < 4; i++) {
          m = Math.min(m, coords[i][0]); // makes the program hava a minimum coord tyype
      }
      return m;
    }


    // the minimum coords for y
    public int minY() 
    {
      int m = coords[0][1];
      for (int i=0; i < 4; i++) {
          m = Math.min(m, coords[i][1]);
      }
      return m;
    }

    // function for rotating shape to the left 
    public Shape rotateLeft() 
    {
        if (pieceShape == Tetrominoes.SquareShape) // square doesnt need to be rotated
            return this;

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) { // inverts coords for other shapes for x axis
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }
        return result;
    }

    // function for rotating shape to the right
    public Shape rotateRight()
    {
        if (pieceShape == Tetrominoes.SquareShape) // square doesnt need to be rotated
            return this;

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) { // inverts coords for y axis
            result.setX(i, -y(i));
            result.setY(i, x(i));
        }
        return result;
    }
}