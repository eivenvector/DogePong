/*
*/

package dogepong;


public class DogeCoin {
    private double[] velocity = new double[2];
    private int[] position = new int[2];
    private final int MAX_Y = 0, MIN_Y = 380, MIN_X = 0, MAX_X = 730;
    
    
    public DogeCoin(int x, int y) {
        setX(x);
        setY(y);
        velocity[0] = (Math.random()*100) % 5 + 8;
        velocity[1] = (Math.random()*100) % 5 + 8;
    }
    
    
    public void setVelocity(double[] array) {
        velocity = array;
    }
    
    public double[] getVelocity() {
        return velocity;
    }
    
    public void setX(int x) {
        position[0] = x;
    }
    
    public void setY(int y) {
        position[1] = y;
    }
    
    public int getX() {
        return position[0];
    }
    
    public int getY() {
        return position[1];
    }
    
    public Boolean hasHitYBounds() {
        if (position[1] > MIN_Y || position[1] < MAX_Y) {
            return true;
        }
        else return false;
    }
    
    public int hasHitXBounds() {
        if (position[0] < MIN_X) {
            return 1;
        } else if (position[0] > MAX_X) {
            return 2;
        } else return 0;
    }
    
    public void wallCollisionVelocityChange() {
        velocity[1] = -velocity[1];
    }
    
    public void dogeCollisionVelocityChange(int negativeFactor) {
        velocity[0] = -velocity[0];
        velocity[1] = negativeFactor * velocity[1];
    }
    
    public Boolean hasHitDoge(int doge_x, int doge_y) {
        int delta_x = doge_x - position[0];
        int delta_y = doge_y - position[1];
        
        
        
        
        if (Math.abs(delta_x) < 50 && Math.abs(delta_y) < 50) {
            System.out.println("Delta X: " + delta_x + "   " + "Delta y: " + delta_y);
            System.out.println("Doge x: " + doge_x + " Doge y: " + doge_y);
            System.out.println("Coin x: " + position[0] + "Coin y: " + position[1]);
            if (delta_y > 0) {
                dogeCollisionVelocityChange(1);
            } else dogeCollisionVelocityChange(-1);
        }
        return false;
    }
}
