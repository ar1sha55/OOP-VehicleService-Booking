public interface InterfaceUser 
    {
        void register();
        void login(String email, String password) throws InvalidLogin;
    }
//naksync