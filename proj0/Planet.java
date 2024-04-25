public class Planet {
    public double xxPos, yyPos, xxVel, yyVel, mass;
    String imgFileName;
    private static final double G = 6.67e-11;
    
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        return Math.sqrt((xxPos - p.xxPos) * (xxPos - p.xxPos) + (yyPos - p.yyPos) * (yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p) {
        double r = calcDistance(p);
        return G * mass * p.mass / (r * r);
    }

    public double calcForceExertedByX(Planet p) {
        double r = calcDistance(p);
        return (p.xxPos - xxPos) / r * calcForceExertedBy(p);
    }

    public double calcForceExertedByY(Planet p) {
        double r = calcDistance(p);
        return (p.yyPos - yyPos) / r * calcForceExertedBy(p);
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double totalForceX = 0;
        for(Planet p : allPlanets) {
            if(this.equals(p)) {
                continue;
            }
            totalForceX += calcForceExertedByX(p);
        }
        return totalForceX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double totalForceY = 0;
        for(Planet p : allPlanets) {
            if(this.equals(p)) {
                continue;
            }
            totalForceY += calcForceExertedByY(p);
        }
        return totalForceY;
    }

    public void update(double time, double xForce, double yForce) {
        double aX = xForce / mass;
        double aY = yForce / mass;
        xxVel += aX * time;
        yyVel += aY * time;
        xxPos += xxVel * time;
        yyPos += yyVel * time;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
