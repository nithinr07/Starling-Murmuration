import java.util.*;
class Main
{
  public static void main(String[] args)
  {
    Scanner in = new Scanner(System.in);
    long test = in.nextLong();
    ArrayList<Long> sum = new ArrayList<>();
    for(int t=0; t<test; t++)
    {
      int n = in.nextInt();
      long k = in.nextLong();
      long min = 50000;
      ArrayList<Long> array = new ArrayList<>();
      for(int i=0; i<n; i++)
      {
        long ele = in.nextInt();
        array.add(ele);
        if(ele < min && ele > k)
          min = ele;
      }
      Collections.sort(array);
      int pos = array.indexOf(min);
      for(int i=pos; i<n-1; i++)
      {
        long a = array.get(i);
        long b = array.get(i+1);
        array.set(i, k);
        array.set(i+1, b-(a-k));
      }
      long tempsum = 0;
      for(int i=0; i<n; i++)
        tempsum += array.get(i);
      sum.add(tempsum);
    }
    for(int i=0; i<test; i++)
      System.out.println(sum.get(i));
  }
}
