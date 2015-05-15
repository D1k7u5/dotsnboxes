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
            int xOffset = 1;
            int tempOffset = 0;
            boolean isXLineHorizontal = false;
            while(xCoordinate > tempRange) {
                counter++;
                xCoordinate -= deltaXItr;
                xOffset += tempOffset;
                
                if(tempRange == lineWidth /2) {
                    tempRange = lineLength/2;
                    tempOffset = 0;
                    isXLineHorizontal = true;
                }
                else{
                    tempRange = lineWidth /2;
                    tempOffset = 1;
                    isXLineHorizontal = false;
                }

            }

            xOffset += tempOffset ;
            lineID = xOffset;

            // detect YOffset
            counter = 0;
            tempRange = lineWidth /2;
            tempOffset = 0;
            int yOffset = 0;
            boolean isYLineHorizontal = false;
            while(yCoordinate > tempRange) {
                counter++;
                yCoordinate -= deltaYItr;
                yOffset += tempOffset;

                if(tempRange == lineWidth /2) {
                    tempOffset = numberOfBoxes;
                    tempRange = lineLength/2;
                    isYLineHorizontal = true;
                }
                else{
                    tempOffset = numberOfBoxes + 1;
                    tempRange = lineWidth /2;
                    isYLineHorizontal = false;
                }
            }
            yOffset += tempOffset ;

            lineID += yOffset;
            if(isXLineHorizontal == isYLineHorizontal) {
                lineID = -1;
            }
        }
        return lineID;
    }
    
}
