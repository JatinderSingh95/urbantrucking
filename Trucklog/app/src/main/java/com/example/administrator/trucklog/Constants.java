package com.example.administrator.trucklog;
import okhttp3.MediaType;



public class Constants {


        public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


        /**
         * TOGGLE BUTTONS FOR LOAD
         */
        public static final int TOGGLE_ALL = 0;
        public static final int TOGGLE_ACTIVE = 1;
        public static final int TOGGLE_INACTIVE = 2;

        /**
         * TOGGLE BUTTONS FOR DRIVER
         */
        public static final int TOGGLE_DRIVER_YARD = 0;
        public static final int TOGGLE_DRIVER_PICK = 1;
        public static final int TOGGLE_DRIVER_DROPPED = 2;


        public static final int MESSAGE_NOTIFITY = 120;

        /**
         * FRAGMENT INDICES
         */
        public static final int FRAGMENT_LOAD_LIST = 0;
        public static final int FRAGMENT_DRIVER_LIST = 1;


        public static final int REQUEST_PERMISSION = 200;


        public static final int SUCCESS_SERVICE_RESULT = 0;
        public static final int FAILURE_SERVICE_RESULT = 1;
        public static final String PACKAGE_NAME = "com.trucklog";
        public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
        public static final String RESULT_DATA_KEY = PACKAGE_NAME +
                ".RESULT_DATA_KEY";
        public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
                ".LOCATION_DATA_EXTRA";


    }




