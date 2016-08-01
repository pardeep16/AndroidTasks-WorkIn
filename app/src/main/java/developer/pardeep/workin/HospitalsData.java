package developer.pardeep.workin;

/**
 * Created by pardeep on 28-06-2016.
 */
public class HospitalsData {
    public String hospitalName;
    public int hospitalImage;
    public String hospitalDescription;

    public HospitalsData(String hospitalName,int hospitalImage,String hospitalDescription) {
        this.hospitalDescription = hospitalDescription;
        this.hospitalImage = hospitalImage;
        this.hospitalName = hospitalName;
    }
}
