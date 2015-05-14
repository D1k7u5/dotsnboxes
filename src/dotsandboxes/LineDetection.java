/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

/**
 *
 * @author kevinbieri
 */
public class LineDetection {
 
    
    private int border;
    private int lineWidth;
    private int lineLength;
    private int numberOfBoxes;
    
    
    public LineDetection() {
    
        border = 40;
        lineWidth = 24;
        lineLength = 80;
        numberOfBoxes = 4;
    }
    
    public LineDetection(int border, int lineWidth, int lineLength, int numberOfBoxesPerLine) {
    
        this.border = border;
        this.lineWidth = lineWidth;
        this.lineLength = lineLength;
        this.numberOfBoxes = numberOfBoxesPerLine;
    }
    
    
    
    public int getLine(int xCoordinate, int yCoordinate) {
        int lineID = -1;
        
        xCoordinate -= border;
        yCoordinate -= border;      // Remove border from Click
        
        int deltaXItr = lineLength / 2 + lineWidth / 2;
        int deltaYItr = deltaXItr;
        
        if(xCoordinate > -(lineWidth/2) && xCoordinate < (numberOfBoxes*2 * deltaXItr + lineWidth/2) && yCoordinate > -(lineWidth/2) && xCoordinate < (numberOfBoxes*2 * deltaXItr + lineWidth/2)) 
        {
        
            // detect XOffset
            int counter = 0;
            int tempRange = lineWidth /2;
            boolean isXLineHorizontal = false;
            while(xCoordinate > tempRange) {
                counter++;
                xCoordinate -= deltaXItr;

                if(tempRange == lineWidth /2) {
                    tempRange = lineLength/2;
                    isXLineHorizontal = true;
                }
                else{
                    tempRange = lineWidth /2;
                    isXLineHorizontal = false;
                }

            }

            int xOffset = (int)(counter/2);
            lineID = xOffset;

            // detect YOffset
            counter = 0;
            tempRange = lineWidth /2;
            boolean isYLineHorizontal = false;
            while(yCoordinate > tempRange) {
                counter++;
                yCoordinate -= deltaYItr;

                if(tempRange == lineWidth /2) {
                    tempRange = lineLength/2;
                    isYLineHorizontal = false;
                }
                else{
                    tempRange = lineWidth /2;
                    isYLineHorizontal = false;
                }
            }
            int yOffset = counter * (numberOfBoxes) ;
            if((counter % 2) == 0) {
                yOffset++;
            }
            
            lineID += yOffset;
            if(isXLineHorizontal == isXLineHorizontal) {
                lineID = -1;
            }
        }
        return lineID;
    }
    
//    public static void main(String[] args){
//          LineDetection ld = new LineDetection(40,24,80,4);
//          int lineId = ld.getLine(144, 144);
//    }
    
}
