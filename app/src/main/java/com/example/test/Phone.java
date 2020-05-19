package com.example.test;

    public class Phone {
        private String firstN,
                lastN,
                Email,
                PhoneNBer,
                Adrss,
                image;

        public Phone(){

        }
        public  String getName() {
            return firstN+" "+lastN;
        }

        public String getFirstN() {
            return firstN;
        }

        public String getLastN() {
            return lastN;
        }

        public String getImage() {
            return image;
        }

        public String getEmail() {
            return Email;
        }

        public String getPhoneNBer() {
            return PhoneNBer;
        }

        public String getAddress() {
            return Adrss;
        }

        public Phone(String firstN, String lastN,String image,String Email, String PhoneNBer,String Adrss){
            this.firstN = firstN;
            this.lastN = lastN;
            this.image = image;
            this.Email = Email;
            this.PhoneNBer = PhoneNBer;
            this.Adrss = Adrss;
        }

    }

