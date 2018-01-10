import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.lang.Math;
import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.Polygon;

public class Parcel extends JComponent
{
  // the 3D array with the coordonnes of the points of every pentomino we're going to use.
  private double[][][] coordOfAll =
    {
      //0 A
      { {-1, 0.5, 0.5}, {-1, 0.5, -0.5}, {-1, -0.5, 0.5}, {-1, -0.5, -0.5}, {1, 0.5, 0.5}, {1, 0.5, -0.5}, {1, -0.5, 0.5}, {1, -0.5, -0.5}, {0,0,0}},
      //1 B
      { {-1, 0.75, 0.5}, {-1, 0.75, -0.5}, {-1, -0.75, 0.5}, {-1, -0.75, -0.5}, {1, 0.75, 0.5}, {1, 0.75, -0.5}, {1, -0.75, 0.5}, {1, -0.75, -0.5}, {0,0,0}},
      //2 C
      { {-0.75, 0.75, 0.75}, {-0.75, 0.75, -0.75}, {-0.75, -0.75, 0.75}, {-0.75, -0.75, -0.75}, {0.75, 0.75, 0.75}, {0.75, 0.75, -0.75}, {0.75, -0.75, 0.75}, {0.75, -0.75, -0.75}, {0,0,0}}
    };
  // the coords of the Parcel that we use are initialised, without any values yet
  private double[][] coords = new double[9][3];
  // the colors of each Pent, im the order
  private Color[] colors = {Color.blue, Color.green, Color.red};
  // initialisation of the color of the Pent that we're going to use
  private Color color;
  //to know wich on we are using
  private int wichOne;
//  private int wichOne;
  private boolean rotatable;
  private int[][] checkCoords = new int[8][3];
  private double xLeft = 200;
  private double yTop = 300;
  private int scale = 70;
  private int perspectiveX = 50;
  private int perspectiveY = 40;

  //constructor
  public Parcel(int wichOne)
  {
    this.wichOne = wichOne;
    color = colors[wichOne];
    for (int i = 0; i < coordOfAll[0].length ; i++)
        for (int j = 0; j < 3; ++j)
            coords[i][j] = coordOfAll[wichOne][i][j]*scale;
  }



  public void rotate2()
  {
    if(rotatable == true)
    {
  	  for(int i = 0; i<8; i++)
      {
  	  coords[i][0] = checkCoords[i][0];
  	  coords[i][1] = checkCoords[i][1];
  	  rotatable = false;
      }
    }
  }

  public void setPlace(int startx, int starty)
  {
    for(int i = 0; i<coordOfAll[0].length; i++){
  		coords[i][0] = coords[i][0]+ startx;
  		coords[i][1] = coords[i][1]+ starty;
  	}
  }

  public int[][] getCheckCoords(){
  	  return checkCoords;

  }

  public Color getColor()
  {
    return color;
  }

  public void setX(int n, int value)
  {
    coords[n][0] = value;
  }

  public void setY(int n, int value)
  {
    coords[n][1] = value;
  }

  public void setZ(int n, int value)
  {
    coords[n][2] = value;
  }

  public void setRotatable(boolean a){
  	  rotatable = a;
  }
/*
  public void setCoords(int x, int y, int value)
  {
    return coords;
  }
*/
  public void setColor(Color c)
  {
    color = c;
  }

  public double[][] getCoords()
  {
    return coords;
  }

  public int getWichOne()
  {
    return wichOne;
  }

  public void draw(Graphics2D g2)
  {
    double[] projPoint = {70.0, 70.0, 210.0};
    double[][] plan = proj2D(coords, projPoint);
    boolean[][] links = { {false,true,true,false,true,false,false,false, false},
                          {true,false,false,true,false,true,false,false, false},
                          {true,false,false,true,false,false,true,false, false},
                          {false,true,true,false,false,false,false,true, false},
                          {true,false,false,false,false,true,true,false, false},
                          {false,true,false,false,true,false,false,true, false},
                          {false,false,true,false,true,false,false,true, false},
                          {false,false,false,true,false,true,true,false, false},
                          {false,false,false,false,false,false,false,false,false}
                        };
  for (int i = 0; i < plan[0].length; i++)
  {
    Point2D.Double point1 = new Point2D.Double((xLeft + plan[0][i]), (yTop + plan[1][i]));
    for(int j = 0; j < plan[0].length; j++)
    if (links[i][j] == true)
    {
      Point2D.Double point2 = new Point2D.Double((xLeft + plan[0][j]), (yTop + plan[1][j]));
      Line2D.Double line = new Line2D.Double(point1, point2);
      g2.draw(line);
    }
  }
  double[][] basis = {{1000,0,0},{0,1000,0},{0,0,1000}};
  double[][] basisRep = proj2D(basis, projPoint);
  Point2D.Double point0 = new Point2D.Double((xLeft), (yTop));
  for(int j = 0; j < 3; j++)
  {
    Point2D.Double point2 = new Point2D.Double((xLeft + basisRep[0][j]), (yTop + basisRep[1][j]));
    Line2D.Double line = new Line2D.Double(point0, point2);
    g2.draw(line);
  }
  // add links between point

  Rectangle2D.Double face1 = new Rectangle2D.Double(xLeft - coords[0][0]*scale, yTop - coords[0][2]*scale, Math.abs(coords[0][0]*scale*2), Math.abs(coords[0][2]*scale*2));
  Rectangle2D.Double face2 = new Rectangle2D.Double(xLeft - coords[0][0]*scale - perspectiveX, yTop - coords[0][2]*scale - perspectiveY, Math.abs(coords[0][0]*scale*2), Math.abs(coords[0][2]*scale*2));

  //Point2D.Double point1 = new Point2D.Double(xLeft + plan[0][0], yTop - coords[0][2]);
  //Point2D.Double point2 = new Point2D.Double(xLeft + coords[0][0], yTop - coords[0][2]);
  //    Point2D.Double point3 = new Point2D.Double(xLeft, yTop);
  //    Line2D.Double line1 = new Line2D.Double(point1, point2);
  //    Line2D.Double line2 = new Line2D.Double(point2, point3);
  //    Line2D.Double line3 = new Line2D.Double(point3, point1);
  g2.draw(face1);
  g2.draw(face2);
  //    g2.draw(line1);
  //    g2.draw(line2);
  //    g2.draw(line3);
  //    g2.draw(p);
}
  public double[][] proj2D(double[][] m3D, double[] projCenter)
  {
    double[][] coordsForProj = new double[m3D[0].length + 1][m3D.length];
    for(int i = 0; i < m3D.length; i++)
      for(int j = 0; j < m3D[0].length; j++)
        coordsForProj[j][i] = m3D[i][j];
    for(int i = 0; i < m3D.length; i++)
      coordsForProj[3][i] = 1;
    printArray(coordsForProj);
    double[][] projMat = { {1, 0, -(projCenter[0]/projCenter[2]), 0}, {0, 1, -(projCenter[1]/projCenter[2]), 0}, {0, 0, 0, 0}, {0, 0, -(1/projCenter[1]), 1} };
    printArray(projMat);
    printArray(multOfMatrices(projMat, coordsForProj));
    return multOfMatrices(projMat, coordsForProj);
  }

  public static double[][] multOfMatrices(double[][] a, double[][] b)
  {
    double[][] mult = new double[a.length][b[0].length];
    for (int k=0; k < a.length; k++)
      for (int l=0; l< b[0].length; l++)
        for (int m=0; m < a[0].length; m++)
          mult[k][l] += a[k][m]*b[m][l];
    return mult;
  }

  public static void printArray(double[][] a)
  {
    for(int i = 0; i < a.length; i++)
    {
      for(int j = 0; j < a[i].length; j++)
      System.out.print(a[i][j] + " ");
      System.out.println("");
    }
    System.out.println("");
  }

public void paintComponent(Graphics g)
{
  Graphics2D g2 = (Graphics2D)g;

  Parcel parcelTest = new Parcel(wichOne);

  parcelTest.draw(g2);
}
}
