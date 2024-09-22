package net.frozenblock.lightsOn.screen;

import net.minecraft.network.chat.Component;

import java.util.function.Function;

public enum ColorInput {
    RGB(Component.literal("RGB"),a -> a),
    HSL(Component.literal("HSL"),a -> {
        int[] hsl = int2rgb(a);
        int h = hsl[0];
        int s = hsl[1];
        int l = hsl[2];

        double hn = h / 360.0;
        double sn = s / 100.0;
        double ln = l / 100.0;

        double c = (1 - Math.abs(2 * ln - 1)) * sn;
        double x = c * (1 - Math.abs((hn * 6) % 2 - 1));
        double m = ln - c / 2;

        double r = 0,g = 0,b = 0;

        if(hn < 1.0 / 6.0) {
            r = c;
            g = x;
        } else if(hn < 2.0 / 6.0) {
            r = x;
            g = c;
        } else if(hn < 3.0 / 6.0) {
            g = c;
            b = x;
        } else if(hn < 4.0 / 6.0) {
            g = x;
            b = c;
        } else if(hn < 5.0 / 6.0) {
            r = x;
            b = c;
        } else {
            r = c;
            b = x;
        }

        int red = (int) ((r + m) * 255);
        int green = (int) ((g + m) * 255);
        int blue = (int) ((b + m) * 255);

        return rgb2int(red, green, blue);
    });


    private final Function<Integer, Integer> func;
    private final Component title;

    ColorInput(Component title,Function<Integer, Integer> func) {
        this.func = func;
        this.title = title;
    }

    public int parse(int r, int g, int b) {
        return func.apply(rgb2int(r,g,b));
    }

    public static int rgb2int(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    public static int[] int2rgb(int index) {
        int r = ((index>>16)&0xFF);
        int g = ((index>>8)&0xFF);
        int b = ((index)&0xFF);
        return new int[]{r, g, b};
    }

    public Component getName() {
        return title;
    }

    public static int[] int2hsl(int index) {
        int[] rgb = int2rgb(index);
        double redNormalized = rgb[0] / 255.0;
        double greenNormalized = rgb[1] / 255.0;
        double blueNormalized = rgb[2] / 255.0;

        double maxColor = Math.max(Math.max(redNormalized, greenNormalized), blueNormalized);
        double minColor = Math.min(Math.min(redNormalized, greenNormalized), blueNormalized);

        double delta = maxColor - minColor;

        double hue = 0, saturation, luminosity;

        if (delta == 0) {
            hue = 0;
        } else if (maxColor == redNormalized) {
            hue = 60 * (((greenNormalized - blueNormalized) / delta) % 6);
        } else if (maxColor == greenNormalized) {
            hue = 60 * (((blueNormalized - redNormalized) / delta) + 2);
        } else if (maxColor == blueNormalized) {
            hue = 60 * (((redNormalized - greenNormalized) / delta) + 4);
        }

        if (hue < 0) {
            hue += 360;
        }

        luminosity = (maxColor + minColor) / 2;

        if (delta == 0) {
            saturation = 0;
        } else {
            saturation = delta / (1 - Math.abs(2 * luminosity - 1));
        }

        int hueInt = (int) Math.round(hue);
        int saturationInt = (int) Math.round(saturation * 100);
        int luminosityInt = (int) Math.round(luminosity * 100);
        return new int[]{hueInt,saturationInt,luminosityInt};
    }


}
