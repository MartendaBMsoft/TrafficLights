package model.enums;

import java.util.ArrayList;
import java.util.List;

public enum FieldTypes {

    TYPE_EMPTY(0,"resources/images/TYPE_EMPTY.png"),
    TYPE_UP(1,"resources/images/TYPE_UP.png"),
    TYPE_RIGHT(2,"resources/images/TYPE_RIGHT.png"),
    TYPE_DOWN(3,"resources/images/TYPE_DOWN.png"),
    TYPE_LEFT(4,"resources/images/TYPE_LEFT.png"),
    TYPE_CROSSING_UP(5,"resources/images/TYPE_CROSSING.png"),
    TYPE_CROSSING_RIGHT(6,"resources/images/TYPE_CROSSING.png"),
    TYPE_CROSSING_DOWN(7,"resources/images/TYPE_CROSSING.png"),
    TYPE_CROSSING_LEFT(8,"resources/images/TYPE_CROSSING.png"),
    TYPE_CROSSING_UP_RIGHT(9,"resources/images/TYPE_CROSSING.png"),
    TYPE_CROSSING_UP_LEFT(10,"resources/images/TYPE_CROSSING.png"),
    TYPE_CROSSING_DOWN_RIGHT(11,"resources/images/TYPE_CROSSING.png"),
    TYPE_CROSSING_DOWN_LEFT(12,"resources/images/TYPE_CROSSING.png");


    private String filePath;
    private int type;

    private FieldTypes(int type, String filePath) {
        this.filePath = filePath;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.filePath;
    }
    
   public static String getRoadType(int number) {
      for (FieldTypes roadType : FieldTypes.values()) {
          if (roadType.type == number)
              return roadType.toString();
      }
        return null;
   }

   public static List<Integer> getStopCells(){
       List<Integer> list = new ArrayList<>();
       for (FieldTypes type:
            FieldTypes.values()) {
           if( (type.type == 5) || (type.type == 6) || (type.type == 7) || (type.type == 8) ||
                   (type.type == 9) || (type.type == 10) || (type.type == 11) || (type.type == 12) ){
               list.add(type.type);
           }
       }
        return list;

   }
}