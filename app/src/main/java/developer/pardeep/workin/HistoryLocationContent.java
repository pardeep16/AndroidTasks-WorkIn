package developer.pardeep.workin;

/**
 * Created by pardeep on 21-06-2016.
 */
public class HistoryLocationContent {

    private String address;
    public static String[] addressList=new String[10];
    public static int listItemCount=0;

    public HistoryLocationContent(String address){
        this.address=address;
        addInList(address);
    }

    private void addInList(String address) {
        if(listItemCount>=9){
            addressList[listItemCount]="";
            listItemCount--;
        }
        if(listItemCount<10){
            addressList[listItemCount]=address;
            listItemCount++;
        }

    }
}
