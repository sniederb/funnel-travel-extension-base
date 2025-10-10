package ch.want.funnel.extension.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.Validate;

/**
 * Map ACRISS code to human-readable text.
 *
 * See <a href='https://www.acriss.org/car-codes/'>Industry Standard Car Classification Code</a>
 */
public class AcrissClassification {

    private static final Map<String, String> PSEUDO_CODES = new HashMap<>();
    private final String acrissCode;
    static {
        PSEUDO_CODES.put("ALLB", "2-3 Door");
        PSEUDO_CODES.put("ALLC", "2 or 4 Door");
        PSEUDO_CODES.put("ALLD", "4-5 Door");
        PSEUDO_CODES.put("AELT", "Any Elite");
        PSEUDO_CODES.put("ACPR", "Couple/Roadster");
        PSEUDO_CODES.put("ASPC", "Non-Standard Fleet");
        PSEUDO_CODES.put("APUP", "Any Pickup");
        PSEUDO_CODES.put("AWGN", "Any Estate/Wagon");
        PSEUDO_CODES.put("AREC", "Camper");
        PSEUDO_CODES.put("ASUV", "Any SUV");
        PSEUDO_CODES.put("AVAN", "Passenger Van");
        PSEUDO_CODES.put("ASIX", "Any 6+ Pax Van");
        PSEUDO_CODES.put("ASEV", "Any 7+ Pax van");
        PSEUDO_CODES.put("AEIG", "Any 8-Pax+ Van");
        PSEUDO_CODES.put("ANIN", "Any 9-Pax+ Van");
        PSEUDO_CODES.put("AFWD", "Any 4WD/ AWD");
        PSEUDO_CODES.put("ATRV", "All Terrain");
        PSEUDO_CODES.put("ACGO", "Commercial Truck");
        PSEUDO_CODES.put("ALMO", "Limousine");
        PSEUDO_CODES.put("ASPT", "Sport");
        PSEUDO_CODES.put("ACNV", "Convertible");
        PSEUDO_CODES.put("AOFR", "Special Offer Car");
        PSEUDO_CODES.put("AMNO", "Monospace");
        PSEUDO_CODES.put("AMTO", "Motor Home");
        PSEUDO_CODES.put("AMCY", "2-Wheel Vehicle");
        PSEUDO_CODES.put("ACRS", "Crossover");
        PSEUDO_CODES.put("AMAN", "All Manual Transmission");
        PSEUDO_CODES.put("AUTO", "All Automatic Transmission");
        PSEUDO_CODES.put("APET", "All Petrol Powered Vehicles");
        PSEUDO_CODES.put("ADSL", "All Diesel Powered Vehicles");
        PSEUDO_CODES.put("AGRN", "Any Lower Emission Vehicles (Hybrid, Electric, LPG, Hydrogen, Ethanol, Multi Fuel)");
        PSEUDO_CODES.put("AHYB", "All Hybrid Vehicles");
        PSEUDO_CODES.put("AELC", "All Electric Powered Vehicles");
        PSEUDO_CODES.put("AHYD", "All Hydrogen Powered Vehicles");
        PSEUDO_CODES.put("AMFP", "All Multi Fuel Powered Vehicles");
        PSEUDO_CODES.put("ACPG", "All LPG/Compressed Gas – Powered Vehicles");
        PSEUDO_CODES.put("AETH", "All Ethanol – Powered Vehicles");
    }

    public AcrissClassification(final String acrissCode) {
        Validate.notNull(acrissCode);
        Validate.isTrue(acrissCode.length() == 4);
        this.acrissCode = acrissCode;
    }

    /**
     * Check an ACRISS code against the list at https://www.acriss.org/car-codes/expanded-matrix/pseudo-car-codes/ .
     */
    public static boolean isPseudoCode(final String acrissCode) {
        return PSEUDO_CODES.containsKey(acrissCode);
    }

    /**
     * Get a vehicle description for an ACRISS pseudo-code.
     */
    public static Optional<String> getPseudoDefinition(final String acrissCode) {
        return Optional.ofNullable(PSEUDO_CODES.get(acrissCode));
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
            return "Recreational Vehicle";
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
