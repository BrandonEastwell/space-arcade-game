package utilities;

//class implemented using lab2 content from moodle
//https://moodle.essex.ac.uk/pluginfile.php/707105/mod_resource/content/5/lab2/lab2.html
public final class Vector2D {
    public double x, y;

    public Vector2D() {
    }

    // constructor for vector with given coordinates
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // constructor that copies the argument vector
    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    // set coordinates
    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // set coordinates based on argument vector
    public Vector2D set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    // compare for equality (note Object type argument)
    public boolean equals(Object o) {
        if (o instanceof Vector2D) {
            Vector2D v = (Vector2D) o;
            return x == v.x && y == v.y;
        } else
            return false;
    }

    // String for displaying vector as text
    public String toString() {
        return "Vector: " + x + "," + y;
    }

    //  magnitude (= "length") of this vector
    public double mag() {
        return Math.hypot(x, y);
    }

    // angle between vector and horizontal axis in radians in range [-PI,PI]
    // can be calculated using Math.atan2
    public double angle() {
        return Math.atan2(y, x);
    }

    // angle between this vector and another vector in range [-PI,PI]
    public double angle(Vector2D other) {
        double angle = other.angle() - this.angle();
        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }  else if (angle <= -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
    }

    public Vector2D add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    // add argument vector
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    // add values to coordinates
    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += fac * v.x;
        this.y += fac * v.y;
        return this;
    }

    // weighted add - surprisingly useful
    public Vector2D subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    // subtract argument vector
    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    // subtract values from coordinates
    public Vector2D mult(double fac) {
        this.x *= fac;
        this.y *= fac;
        return this;
    }

    // multiply with factor
    public Vector2D rotate(double angle) {
        double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        this.y = this.x * Math.sin(angle) + this.y * Math.cos(angle);
        this.x = newX;
        return this;
    }

    // rotate by angle given in radians
    public double dot(Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }

    // "dot product" ("scalar product") with argument vector
    public double dist(Vector2D v, double w, double h) {
        double x = this.x - v.x;
        double y = this.y - v.y;
        if (x > w/2) {
            x = w - x;
        }
        else if (x < -w/2) {
            x = -w - x;
        }
        if (y > h/2) {
            y = h - y;
        }
        else if (y < -h/2) {
            y = -h - y;
        }
        return Math.hypot(x, y);
    }

    // distance to argument vector, considers wrap-around
    public Vector2D normalise() {
        double mag = mag();
        this.x /= mag;
        this.y /= mag;
        return this;

    }

    // normalise vector so that magnitude becomes 1
    public void wrap(double w, double h) {
        if (this.x > w) {
            this.x -= w;
        }
        else if (x < 0) {
            this.x+= w;
        }
        if ( this.y>h) {
            this.y -= h;
        }
        else if (y < 0){
            this.y += h;
        }
    }

    // construct vector with given polar coordinates
    public static Vector2D polar(double angle, double mag) {
        return new Vector2D(mag * Math.cos(angle), mag * Math.sin(angle));
    }

}
