import java.util.HashMap;

/**
 * task2
 */
public class task2
{
    private static String[] employees = 
    {
        "Иван Иванов",
        "Светлана Петрова",
        "Кристина Белова",
        "Анна Мусина",
        "Анна Крутова",
        "Иван Юрин",
        "Петр Лыков",
        "Павел Чернов",
        "Петр Чернышов",
        "Мария Федорова",
        "Марина Светлова",
        "Мария Савина",
        "Мария Рыкова",
        "Марина Лугова",
        "Анна Владимирова",
        "Иван Мечников",
        "Петр Петин",
        "Иван Ежов"
    };
    
    public static void main(String[] args)
    {
        var nameRepeats = SearchNameRepeats();
        SortByRepeats(nameRepeats);
        System.out.println(NameRepeatsToString(nameRepeats));
    }

    private static HashMap<String,Integer> SearchNameRepeats()
    {
        var nameRepeats = new HashMap<String,Integer>();
        for(int i = 0; i < employees.length; i++)
        {
            String name = GetNameFromFullName(employees[i]);
            int repeat = 1;
            if(nameRepeats.containsKey(name))
                repeat += nameRepeats.get(name);
            nameRepeats.put(name, repeat);
        }
        return nameRepeats;
    }
    
    private static String GetNameFromFullName(String fullName)
    {
        String[] split = fullName.split(" ");
        return split[0];
    }

    private static void SortByRepeats(HashMap<String,Integer> nameRepeats)
    {
        String[] arr = nameRepeats.keySet().toArray(new String[0]);
        for(int j = 1; j < arr.length; j++)
        {
            Boolean f = false;
            for (int i = 0; i < arr.length - j; i++)
            {
                if(nameRepeats.get(arr[i]) < nameRepeats.get(arr[i + 1]))
                {
                    int t = nameRepeats.get(arr[i]);
                    nameRepeats.put(arr[i], nameRepeats.get(arr[i + 1]));
                    nameRepeats.put(arr[i + 1], t);
                    f = true;
                }
            }
            if(!f) break;
        }
    }

    private static String NameRepeatsToString(HashMap<String,Integer> nameRepeats)
    {
        var builder = new StringBuilder();
        for(var nameRepeat : nameRepeats.entrySet())
        {
            String name = nameRepeat.getKey();
            int repeat = nameRepeat.getValue();
            String str = String.format("Имя '%s' имеет %s повторений\n", name, repeat);
            builder.append(str);
        }
        return builder.toString();
    }
}