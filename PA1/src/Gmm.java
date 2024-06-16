import java.lang.Math;
public class Gmm {
    private String[] args;
    private String[] produtctsFromFile;
    private String[] purchasesFromFile;
    private Product[] productsFromMachine;
    private Slot[] slots = new Slot[24];

    public Gmm(String[] args, String[] produtctsFromFile, String[] purchasesFromFile, Product[] productsFromMachine)
    {
        this.args = args;
        this.produtctsFromFile = produtctsFromFile;
        this.purchasesFromFile = purchasesFromFile;
        this.productsFromMachine = productsFromMachine;  
    }

    /**
     * Checks whether slots are fully occupied
     * 
     * @return -1 if all slots are fully occupied, otherwise 0
     */
    public int fill()
    {
        int totalProduct = 0;
        
        for(Slot s: this.slots)
        {
            totalProduct += s.getCount();
        } 

        return totalProduct >= 240 ? -1 : 0;
    }

    /**
     * Creates slots for GMM
     */
    public void createSlots()
    {
        for(int i = 0 ; i< 24;i++)
        {
            this.slots[i] = new Slot();
        }
    }

    public int writeInfoMessage(int statusCode,String name)
    {
        final String[] infos = {
            "INFO: There is no available place to put "+name, // Status 0
            "INFO: The machine is full!", // Status 1
            "INFO: Insufficient money, try again with more money.", // Status 2
            "INFO: Some invalid coins were detected. These coins will not be used.", // Status 3
            "INFO: Number cannot be accepted. Please try again with another number.", // Status 4
            "INFO: This slot is empty, your money will be returned.", // Status 5
            "INFO: Product not found, your money will be returned." // Status 6
        };
        FileIO.writeFile(args[2], infos[statusCode],true, true );
        return -1;
    }
    /**
     * Creates product objects from the product list, arranges them in 6 rows and 4 columns and writes them to the file.
     */
    public void createProducts()
    {
        for (int i = 0 ; i < this.produtctsFromFile.length;i++)
        {
            String[] productSample = produtctsFromFile[i].split("\\t"); // Splits rows into [product_name,price,product_data]
            String[] productData = productSample[2].split(" "); // Transfers the product's properties to an array
            this.productsFromMachine[i] = new Product(productSample[0],Integer.parseInt(productSample[1])
            ,Float.parseFloat(productData[0]),Float.parseFloat(productData[1]),
            Float.parseFloat(productData[2])); 
            Boolean isFoundEmptySlot = false;

            for(Slot s: this.slots) // Places products in slots
            {
                if(s.getProduct().getName() == "___")
                {
                    s.setProduct(this.productsFromMachine[i]);
                    s.setCount(s.getCount()+1);
                    isFoundEmptySlot = true;
                    break;
                }
                else if(s.getProduct().getName().equals(productSample[0]) && s.getCount() < 10)
                {
                    s.setCount(s.getCount()+1);
                    isFoundEmptySlot = true;
                    break;
                }
            }

            if(!isFoundEmptySlot) // Situation where there is no space left to place
            {
                if(fill() == -1)
                {
                    writeInfoMessage(0, productSample[0]);
                    writeInfoMessage(1,null);
                    break;
                }
                else
                {
                    writeInfoMessage(0, productSample[0]);
                }
            }
        }
        writeSlotInfo();
    }

    /**
     * Arrange the products in 6 rows and 4 columns and write them to the file.
     */
    public void writeSlotInfo()
    {
        FileIO.writeFile(args[2], "-----Gym Meal Machine-----", true, true);
        int counter = 1;
        for(Slot s: this.slots)
        {
            FileIO.writeFile(args[2], s.getProduct().getName()+ "("+ Math.round(s.getProduct().getCalorie())+", "+s.getCount()+")___", true, counter%4==0);
            counter ++;
        }  
        FileIO.writeFile(args[2], "----------", true, true);
    }

    /**
     * Checks whether the product to be purchased is budget enough
     * 
     * @param budget User's total budget
     * @param s      Current slot object
     * @return true: Indicates that you have a product with valid features
     */
    public boolean checkBudget(int budget,Slot s)
    {
        if(budget >= s.getProduct().getPrice())
        {
            FileIO.writeFile(args[2], "PURCHASE: You have bought one " + s.getProduct().getName(), true,true );
            FileIO.writeFile(args[2], "RETURN: Returning your change: " + (budget-s.getProduct().getPrice()) + " TL", true,true );
            s.setCount(s.getCount()-1);
            if(s.getCount() == 0)
                s.resetSlot();
        }
        else
        {
            writeInfoMessage(2, null);
            FileIO.writeFile(args[2], "RETURN: Returning your change: " + budget + " TL", true,true );
        }
        return true;
    }
    
    /**
     * Attempts to purchase products by following the instructions in the purchase list
     */
    public void buyProducts()
    {
        for (int i = 0 ; i < this.purchasesFromFile.length;i++)
        {
            String[] productSample = purchasesFromFile[i].split("\\t"); // Splits rows into [purchase_type,budget,class_selection,range]
            String[] productData = productSample[1].split(" "); // Collects the moneys put into the machine into a list
            int budget=0;
            int counter=-1; // Currently available slot number
            int invalidMoneyCounter = 0;
            boolean isProductFound = false;
            
            FileIO.writeFile(args[2], "INPUT: "+purchasesFromFile[i], true,true );
            for (String x:productData)
            {
                int tempMoney = 0;
                try
                {
                    tempMoney = Integer.parseInt(x);
                }    
                catch (Exception e) {
                    tempMoney = 0; // In case of invalid entry, set the money equal to 0
                }
    
                if(tempMoney == 1 || tempMoney == 5 || tempMoney == 10 || tempMoney == 20 || tempMoney == 50 || tempMoney == 100 || tempMoney == 200)
                {
                    budget += tempMoney;
                }
                else
                {
                    invalidMoneyCounter += 1;
                    if(invalidMoneyCounter == 1)
                    {
                        writeInfoMessage(3, null);
                    }
                }
            }
            for(Slot s: this.slots)
            {
                counter ++;
                if(productSample[2].equals("PROTEIN") && s.getProduct().getName() != "___")
                {
                    if ((Math.abs(s.getProduct().getProtein() - Float.parseFloat(productSample[3])) <= 5.0))
                    {
                        isProductFound = checkBudget(budget,s);
                        break;
                    }
                    else
                    {
                        continue;
                    }
                }
                else if(productSample[2].equals("CARB") && s.getProduct().getName() != "___")
                {
                    if ((Math.abs(s.getProduct().getCarbonhydrate() - Float.parseFloat(productSample[3])) <= 5.0))
                    {
                        isProductFound = checkBudget(budget,s);
                        break;
                    }
                    else
                    {
                        continue;
                    }
                }
                else if(productSample[2].equals("FAT") && s.getProduct().getName() != "___")
                {
                    if ((Math.abs(s.getProduct().getFat() - Float.parseFloat(productSample[3])) <= 5.0))
                    {
                        isProductFound = checkBudget(budget,s);
                        break;
                    }
                    else
                    {
                        continue;
                    }
                }
                else if(productSample[2].equals("CALORIE") && s.getProduct().getName() != "___")
                {
                    if ((Math.abs(s.getProduct().getCalorie() - Float.parseFloat(productSample[3])) <= 5.0))
                    {
                        isProductFound = checkBudget(budget,s);
                        break;
                    }
                    else
                    {
                        continue;
                    }
                }
                else if(productSample[2].equals("NUMBER"))
                {
                    if(Integer.parseInt(productSample[3]) > 23 || Integer.parseInt(productSample[3]) <0)
                    {
                        writeInfoMessage(4, null);
                        FileIO.writeFile(args[2], "RETURN: Returning your change: " + budget + " TL", true,true );
                        isProductFound = true;
                        break;
                    }
                    else if(Integer.parseInt(productSample[3]) == counter)
                    {
                        if (s.getProduct().getName() == "___")
                        {
                            writeInfoMessage(5, null);
                            FileIO.writeFile(args[2], "RETURN: Returning your change: " + budget + " TL", true,true );
                        }
                        else
                        {
                            isProductFound = checkBudget(budget,s);
                        }
                        isProductFound = true;
                        break; 
                    }
                    else
                    {
                        continue;
                    }
                }
            }
            if(!isProductFound)
            {
                writeInfoMessage(6, null);
                FileIO.writeFile(args[2], "RETURN: Returning your change: " + budget + " TL", true,true );
            }
        }
    }
}
