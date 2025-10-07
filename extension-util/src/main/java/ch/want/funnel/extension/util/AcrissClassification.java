package ch.want.funnel.extension.util;

import org.apache.commons.lang3.Validate;

/**
 * Map ACRISS code to human-readable text.
 *
 * See <a href='https://www.acriss.org/car-codes/'>Industry Standard Car Classification Code</a>
 */
public class AcrissClassification {

    private final String acrissCode;

    public AcrissClassification(final String acrissCode) {
        Validate.notNull(acrissCode);
        Validate.isTrue(acrissCode.length() == 4);
        this.acrissCode = acrissCode;
    }

    public String getCategory() {
        switch (acrissCode.charAt(0)) {
        case 'M':
            return "Mini";
        case 'N':
            return "Mini Elite";
        case 'E':
            return "Economy";
        case 'H':
            return "Economy Elite";
        case 'C':
            return "Compact";
        case 'D':
            return "Compact Elite";
        case 'I':
            return "Intermediate";
        case 'J':
            return "Intermediate Elite";
        case 'S':
            return "Standard";
        case 'R':
            return "Standard Elite";
        case 'F':
            return "Fullsize";
        case 'G':
            return "Fullsize Elite";
        case 'P':
            return "Premium";
        case 'U':
            return "Premium Elite";
        case 'L':
            return "Luxury";
        case 'W':
            return "Luxury Elite";
        case 'O':
            return "Oversize";
        case 'X':
            return "Special";
        default:
            return "";
        }
    }

    public String getType() {
        switch (acrissCode.charAt(1)) {
        case 'B':
            return "2-3 Door";
        case 'C':
            return "2/4 Door";
        case 'D':
            return "4-5 Door";
        case 'W':
            return "Wagon/Estate";
        case 'V':
            return "Passenger Van";
        case 'L':
            return "Limousine/Sedan";
        case 'S':
            return "Sport";
        case 'T':
            return "Convertible";
        case 'F':
            return "SUV";
        case 'J':
            return "Open Air All Terrain";
        case 'X':
            return "Special";
        case 'P':
            return "Pick up (single/extended cab) 2 door";
        case 'Q':
            return "Pick up (double cab) 4 door";
        case 'Z':
            return "Special Offer Car";
        case 'E':
            return "Coupe";
        case 'M':
            return "Monospace";
        case 'R':
            return "Recreational Vehicle"; // motorhome or a smaller campervan
        case 'H':
            return "Motor Home";
        case 'Y':
            return "2 Wheel Vehicle";
        case 'N':
            return "Roadster";
        case 'G':
            return "Crossover";
        case 'K':
            return "Commercial Van/Truck";
        default:
            return "";
        }
    }

    public String getTransmission() {
        switch (acrissCode.charAt(2)) {
        case 'M':
            return "Manual Unspecified Drive";
        case 'N':
            return "Manual 4WD";
        case 'C':
            return "Manual AWD";
        case 'A':
            return "Auto Unspecified Drive";
        case 'B':
            return "Auto 4WD";
        case 'D':
            return "Auto AWD";
        default:
            return "";
        }
    }

    public String getExtras() {
        switch (acrissCode.charAt(3)) {
        case 'R':
            return "Unspecified Fuel/Power With Air";
        case 'N':
            return "Unspecified Fuel/Power Without Air";
        case 'D':
            return "Diesel Air";
        case 'Q':
            return "Diesel No Air";
        case 'H':
            return "Hybrid";
        case 'I':
            return "Hybrid Plug in";
        case 'E':
        case 'C':
            return "Electric";
        case 'L':
            return "LPG/Compressed Gas Air";
        case 'S':
            return "LPG/Compressed Gas No Air";
        case 'A':
            return "Hydrogen Air";
        case 'B':
            return "Hydrogen No Air";
        case 'M':
            return "Multi Fuel/Power Air";
        case 'F':
            return "Multi fuel/power No Air";
        case 'V':
            return "Petrol Air";
        case 'Z':
            return "Petrol No Air";
        case 'U':
            return "Ethanol Air";
        case 'X':
            return "Ethanol No Air";
        default:
            return "";
        }
    }
}
