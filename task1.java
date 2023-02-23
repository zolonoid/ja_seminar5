import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * task1
 */
public class task1
{
    public static void main(String[] args)
    {
        try
        {
            new PhoneBookCmd(new PhoneBook("task1.csv"));
        }
        catch (Exception e)
        {
            System.out.printf("Ошибка: %s", e.getMessage());
        }
    }
}

class PhoneBook
{
    private HashMap<String,String[]> _contacts;
    private Path _csvFile;

    PhoneBook(String csvFile) throws IOException
    {
        _contacts = new HashMap<String,String[]>();
        _csvFile = Path.of(csvFile);
        if(!Files.exists(_csvFile)) return;
        for(String contact : Files.readAllLines(_csvFile))
        {
            String[] split = contact.split(";");
            String[] phoneNumbers = Arrays.copyOfRange(split, 1, split.length);
            _contacts.put(split[0], phoneNumbers);
        }
    }

    public void AddContact(String name, String[] phoneNumbers) throws IOException
    {
        _contacts.put(name, phoneNumbers);
        Save();
    }

    public void DelContact(String name) throws IOException
    {
        _contacts.remove(name);
        Save();
    }
    
    public String[] GetContact(String name)
    {
        return _contacts.get(name);
    }
    
    public void Save() throws IOException
    {
        var contacts = new ArrayList<String>();
        for(var contact : _contacts.entrySet())
        {
            List<String> phoneNumbers = Arrays.asList(contact.getValue());
            phoneNumbers.add(0, contact.getKey());
            String csvStr = String.join(";", phoneNumbers);
            contacts.add(csvStr);
        }
        Files.write(_csvFile, contacts, StandardCharsets.UTF_8);
    }

    @Override
    public String toString()
    {
        StringBuilder contacts = new StringBuilder();
        for (var contact : _contacts.entrySet())
        {
            List<String> phoneNumbers = Arrays.asList(contact.getValue());
            contacts.append(String.format("%s  %s\n", contact.getKey(), String.join(", ", phoneNumbers)));
        }
        return contacts.toString();
    }
}

class PhoneBookCmd
{
    private PhoneBook _phoneBook;
    private Scanner _sc;
    private Command[] _commands;
    
    PhoneBookCmd(PhoneBook phoneBook) throws IOException
    {
        try
        {
            _sc = new Scanner(System.in, System.console().charset());
            _phoneBook = phoneBook;
            _commands = new Command[] { new Add(), new Del(), new All(), new Get(), new End() };
            for (int i = 0; i < _commands.length; i++)
                System.out.printf("%s:\n    %s\n", _commands[i].Description(), _commands[i].Syntax());
            Run();
        }
        finally
        {
            if(_sc != null)
                _sc.close();

        }
    }

    private void Run() throws IOException
    {
        while(true)
        {
            String input = _sc.nextLine();
            if(input.length() == 0) break;
            String out = "Неизвестная команда";
            for(Command command : _commands)
            {
                String result = command.ProcessCommand(input);
                if(result == null) continue;
                out = result;
                break;
            }
            if(out == "") break;
            System.out.println(out);
        }
    }
    
    private abstract class Command
    {
        private static Pattern commandPattern = Pattern.compile(
            "^([a-zA-Z]+)(?:\\s|$)"
        );
        private static Pattern paramPattern = Pattern.compile(
            "\\s(-[a-zA-Z]+)\\s(.+?)(?:\\s-[a-zA-Z]+\\s|$)"
        );
        
        protected String _input;
        protected String _command;
        protected HashMap<String,ArrayList<String>> _params = new HashMap<String,ArrayList<String>>();
        
        public String ProcessCommand(String input) throws IOException
        {
            _input = input;
            SearchCommand();
            if(_command.length() == 0) return null;
            SearchParams();
            return ProcessCommand();
        }
        
        private void SearchCommand()
        {
            Matcher match = commandPattern.matcher(_input);
            if(match.find())
                _command = match.group(1);
        }

        private void SearchParams()
        {
            Matcher match = paramPattern.matcher(_input);
            while(match.find())
            {
                String param = match.group(1);
                String value = match.group(2);
                if(!_params.containsKey(param))
                    _params.put(param, new ArrayList<String>());
                _params.get(param).add(value);
            }
        }

        protected String SyntaxError()
        {
            return
                String.format("Неверный синтаксис для команды %s:\n%s", this, Syntax());
        }
        
        public abstract String Syntax();
        public abstract String Description();
        protected abstract String ProcessCommand() throws IOException;
    }
    
    // ADD -n имя -t телефон [-t телефон]
    private class Add extends Command
    {
        @Override
        public String Syntax() {
            return "ADD -n имя -t телефон [-t телефон]";
        }
        
        @Override
        public String Description() {
            return "Добавить контакт";
        }
        
        @Override
        public String toString() {
            return "ADD";
        }
        
        @Override
        protected String ProcessCommand() throws IOException
        {
            if(!_command.equalsIgnoreCase("ADD"))
                return null;
            ArrayList<String> n = _params.get("-n");
            if(n == null)
                return SyntaxError();
            ArrayList<String> t = _params.get("-t");
            if(t == null)
                return SyntaxError();
            return Success(n.get(0), (String[])t.toArray());
        }

        private String Success(String n, String[] t) throws IOException
        {
            _phoneBook.AddContact(n, t);
            return "Контакт добавлен.";
        }
    }
    
    // DEL -n имя
    private class Del extends Command
    {
        @Override
        public String Syntax() {
            return "DEL -n имя";
        }
        
        @Override
        public String Description() {
            return "Удалить контакт";
        }
        
        @Override
        public String toString() {
            return "DEL";
        }
        
        @Override
        protected String ProcessCommand() throws IOException
        {
            if(!_command.equalsIgnoreCase("DEL"))
                return null;
            ArrayList<String> n = _params.get("-n");
            if(n == null)
                return SyntaxError();
            return Success(n.get(0));
        }

        private String Success(String n) throws IOException
        {
            _phoneBook.DelContact(n);
            return "Контакт удален.";
        }
    }
    
    // ALL
    private class All extends Command
    {
        @Override
        public String Syntax() {
            return "ALL";
        }
        
        @Override
        public String Description() {
            return "Показать все контакты";
        }
        
        @Override
        public String toString() {
            return "ALL";
        }
        
        @Override
        protected String ProcessCommand()
        {
            if(!_command.equalsIgnoreCase("ALL"))
                return null;
            return Success();
        }
        
        private String Success()
        {
            return _phoneBook.toString();
        }
    }
    
    // GET -n имя
    private class Get extends Command
    {
        @Override
        public String Syntax() {
            return "GET -n имя";
        }
        
        @Override
        public String Description() {
            return "Получить номер телефона";
        }
        
        @Override
        public String toString() {
            return "GET";
        }
        
        @Override
        protected String ProcessCommand()
        {
            if(!_command.equalsIgnoreCase("GET"))
                return null;
            ArrayList<String> n = _params.get("-n");
            if(n == null)
                return SyntaxError();
            return Success(n.get(0));
        }
        
        private String Success(String n)
        {
            var contact = _phoneBook.GetContact(n);
            if(contact == null)
                return "Контакт не найден.";
            List<String> phoneNumbers = Arrays.asList(contact);
            return String.format("%s  %s", n, String.join(", ", phoneNumbers));
        }
    }


    // END
    private class End extends Command
    {
        @Override
        public String Syntax() {
            return "END";
        }
        
        @Override
        public String Description() {
            return "Завершить работу";
        }
        
        @Override
        public String toString() {
            return "END";
        }
        
        @Override
        protected String ProcessCommand()
        {
            if(!_command.equalsIgnoreCase("END"))
                return null;
            return "";
        }
    }
}
