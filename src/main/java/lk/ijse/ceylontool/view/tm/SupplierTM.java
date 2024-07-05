package lk.ijse.ceylontool.view.tm;




















    public class SupplierTM implements Comparable<SupplierTM>{


        private String id;
        private String name;
        private String address;
        private int tp;

        public SupplierTM() {
        }

        public SupplierTM(String id, String name, String address, int  tp) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.tp = tp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


        public int getTp() {
            return tp;
        }

        public void setTp(int tp) {
            this.tp = tp;
        }


        @Override
        public String toString() {
            return "CustomerDTO{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }

        @Override
        public int compareTo(SupplierTM o) {
            return id.compareTo(o.getId());
        }
    }









