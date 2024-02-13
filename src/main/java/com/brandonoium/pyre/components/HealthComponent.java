package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class HealthComponent implements IComponent {
    private int maxHealth;
    private int currentHealth;


    public HealthComponent(int maxHealth, int currentHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
    }

    public HealthComponent(int maxHealth) {
        this(maxHealth, maxHealth);
    }


    public void damageHealth(int damage) {
        currentHealth -= damage;
        if(currentHealth < 0)
            currentHealth = 0;
    }

    public void restoreHealth(int healing) {
        currentHealth += healing;
        if(currentHealth > maxHealth)
            currentHealth = maxHealth;
    }

    public boolean isDead() {
        return currentHealth == 0;
    }


    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
}
