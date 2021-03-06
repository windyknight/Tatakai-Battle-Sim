/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlesim;

import java.text.DecimalFormat;

/**
 *
 * @author Jay Lopez
 */
public class Skeptic extends Warrior {
    private final int mysticSpecial = 5;
    private int mysticCount = 0;
    private final int bruteSpecial = 6;
    private int bruteCount = 0;
    DecimalFormat a = new DecimalFormat("0");
    DecimalFormat b = new DecimalFormat("0.00");
    
    Skeptic(String name, Type type, int baseTough, int incTough, int baseDex, int incDex, int baseSmart, int incSmart, double baseArmor, double minDamage, double maxDamage, double baseAttackTime) {
        super(name, type, baseTough, incTough, baseDex, incDex, baseSmart, incSmart, baseArmor, minDamage, maxDamage, baseAttackTime);
    }
    
    public void attack(Mystic target) {
        double range = maxDamage - minDamage;
        range += Math.random() * range + minDamage;
        if (type == Type.TOUGH) {
            range += tough;
        }
        if (type == Type.DEXTEROUS) {
            range += dex;
        }
        if (type == Type.SMART) {
            range += smart;
        }
        if (mysticCount >= mysticSpecial) {
            mysticCount = 0;
            range += 0.05 * target.getEnergy();
        }
        target.takeDamage(range);
        mysticCount++;
        System.out.println(type + " " + name + " attacks for " + a.format(range) + " damage!"); 
    }
    
    public void attack(Brute target) {
        double range = maxDamage - minDamage;
        range += Math.random() * range + minDamage;
        if (type == Type.TOUGH) {
            range += tough;
        }
        if (type == Type.DEXTEROUS) {
            range += dex;
        }
        if (type == Type.SMART) {
            range += smart;
        }
        if (bruteCount >= bruteSpecial) {
            bruteCount = -1;
            target.takeDamage(range, 2.00);
        }
        else {
            target.takeDamage(range);
        }
        bruteCount++;
        System.out.println(type + " " + name + " attacks for " + a.format(range) + " damage!"); 
    }
    
    public void reset() {
        fullHeal();
        timeBack();
        mysticCount = 0;
        bruteCount = 0;
    }
    
    public String toString() {
        return type + " " + "Skeptical" + " " + name + ": " + maxHp + "HP, " + tough + "TOU, " + dex + "DEX, " + smart + "SMR, " + b.format(trueArmor) + "ARM, " + baseAttackTime + "SPD";
    }
}
