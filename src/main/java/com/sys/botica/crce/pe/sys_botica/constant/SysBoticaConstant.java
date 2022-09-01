package com.sys.botica.crce.pe.sys_botica.constant;

public final class SysBoticaConstant {

	// =============================================================================================
	// CODIGO DE ERROR DEL CLIENTE Y SERVIDOR
	// =============================================================================================

	// CLIENT ERRORS
	public static final String BAD_REQUEST = "401";
	public static final String UNAUTHORIZED = "402";
	public static final String FORBIDDEN = "403";
	public static final String NOT_FOUND = "404";
	public static final String METHOD_NOT_ALLOWED = "405";
	public static final String NOT_ACCEPTABLE = "406";
	public static final String CONFLICT = "409";
	public static final String UNPROCESSABLE_ENTITY = "422";
	public static final String EXPECTATION_FAILED = "417";
	public static final Float NR_VUELTO_DEFAULT = (float) 0;
	public static final String VC_NRO_DOCUMENTO = "10469643595";
	public static final String VC_TYPE_SALE = "CONTADO";
	public static final String VC_NR_TELEPHONE = "954395553";

	// SERVER ERRORS
	public static final String INTERNAL_SERVER_ERROR = "500";
	public static final String NOT_IMPLEMENTED = "502";
	public static final String BAD_GATEWAY = "503";
	public static final String SERVICE_UNAVAILABLE = "504";
	public static final String GATEWAY_TIMEOUT = "505";
	public static final String NOT_VALIDATED = "506";

	// ERRORS
	public static final String PREFIX_SERVER_ERROR = "SRV";
	public static final String PREFIX_CLIENT_ERROR = "CLI";

	// STATES
	public static final String STATE_ACTIVE = "1";
	public static final String STATE_INACTIVE = "0";
	
	// TYPE WHAREHOUSE
	public static final String TYPE_DISTRIBUTION_WHAREHOUSE = "1";
	public static final String TYPE_SUB_WHAREHOUSE = "0";
	
	//VERSION API
	public static final String API_VERSION = "v1";
	
	//API CLOUDINARY
	public static final String API_CLOUDINARY_NAME = "cloud_name";
	public static final String API_CLOUDINARY_KEY = "api_key";
	public static final String API_CLOUDINARY_SECRET = "api_secret";
	
	//CODE HTML TEMPLATE
	public static final String HTML_SEND_EMAIL = "SEND_CODE_VERIFY";
	
	//NAME EMTERPRISE
	public static final String TITLE_NAME_ENTERPRISE = "Huatatas Clud";


	// =============================================================================================
	// PATH DEL SISTEMA ADMINISTRADOR SYSCE
	// =============================================================================================
	public static final String RESOURCE_GENERIC = "/sysce-app";
	public static final String RESOURCE_CATEGORYS = RESOURCE_GENERIC + "/categories";
	public static final String RESOURCE_CATEGORYS_CATEGORY = "/category";
	public static final String RESOURCE_WHAREHOUSESUBSIDIARYS = RESOURCE_GENERIC + "/wharehouses-subsidiaries";
	public static final String RESOURCE_WHAREHOUSESUBSIDIARYS_WHAREHOUSESUBSIDIARY = "/wharehouse-subsidiary";
	public static final String RESOURCE_REPORTS = RESOURCE_GENERIC + "/reports";
	public static final String RESOURCE_REPORTS_REPORT = "/report";
	public static final String RESOURCE_PROOFPAYMENTS = RESOURCE_GENERIC + "/proof-payments";
	public static final String RESOURCE_PROOFPAYMENTS_PROOFPAYMENT = "/proof-payment";
	public static final String RESOURCE_DTTRANSFERS = RESOURCE_GENERIC + "/dt-transfers";
	public static final String RESOURCE_DTTRANSFERS_DTTRANSFER = "/dt-transfer";
	public static final String RESOURCE_CUSTOMERS = RESOURCE_GENERIC + "/customers";
	public static final String RESOURCE_CUSTOMERS_CUSTOMER = "/customer";
	public static final String RESOURCE_TYPEDOCUMENTS = RESOURCE_GENERIC + "/types-documents";
	public static final String RESOURCE_TYPEDOCUMENTS_TYPEDOCUMENT = "/type-document";
	public static final String RESOURCE_MARKS = RESOURCE_GENERIC + "/marks";
	public static final String RESOURCE_MARKS_MARK = "/mark";
	public static final String RESOURCE_UNITS = RESOURCE_GENERIC + "/units";
	public static final String RESOURCE_UNITS_UNIT = "/unit";
	public static final String RESOURCE_PRODUCTS = RESOURCE_GENERIC + "/products";
	public static final String RESOURCE_PRODUCTS_PRODUCT = "/product";
	public static final String RESOURCE_USERS = RESOURCE_GENERIC + "/users";
	public static final String RESOURCE_USERS_USER = "/user";
	public static final String RESOURCE_GENERIC_AUTO_COMPLETE = "/auto-complete";
	public static final String RESOURCE_GENERIC_CHANGE_PASSWORD = "/change-password";
	public static final String RESOURCE_USERSUBSIDIARYS = RESOURCE_GENERIC + "/users-enterprises";
	public static final String RESOURCE_USERSUBSIDIARYS_USERSUBSIDIARY = "/user-enterprise";
	public static final String RESOURCE_SUBSIDIARYS = RESOURCE_GENERIC + "/subsidiaries";
	public static final String RESOURCE_SECURITYPOLICYS = RESOURCE_GENERIC + "/securities-policies";
	public static final String RESOURCE_SECURITYPOLICYS_SECURITYPOLICY = "/security-policy";
	public static final String RESOURCE_SUBSIDIARYS_SUBSIDIARY = "/subsidiary";
	public static final String RESOURCE_SIP = RESOURCE_GENERIC + "/sip";
	public static final String RESOURCE_LOGIN = "/login";
	public static final String RESOURCE_SIP_PERMIT_ALL = "/v1/sysce-app/sip/login";
	public static final String RESOURCE_USER_SUBSIDIARY_PERMIT_ALL = "/v1/sysce-app/users-enterprises/user-enterprise**";
	public static final String RESOURCE_AUTHORITYS_AUTHORITY = "/authority";
	public static final String RESOURCE_PROVIDERS = RESOURCE_GENERIC + "/providers";
	public static final String RESOURCE_PROVIDERS_PROVIDER = "/provider";
	public static final String RESOURCE_USERAUTHORITYS = RESOURCE_GENERIC + "/users-authorities";
	public static final String RESOURCE_USERAUTHORITYS_USERAUTHORITY = "/user-autority";
	public static final String RESOURCE_WHAREHOUSES = RESOURCE_GENERIC + "/wharehouses";
	public static final String RESOURCE_WHAREHOUSES_WHAREHOUSE = "/wharehouse";
	public static final String RESOURCE_ENTRYDOCUMENTS = RESOURCE_GENERIC + "/entries-documents";
	public static final String RESOURCE_ENTRYDOCUMENTS_ENTRYDOCUMENT = "/entry-document";
	public static final String RESOURCE_ENTRYPRODUCTS = RESOURCE_GENERIC + "/entries-products";
	public static final String RESOURCE_ENTRYPRODUCTS_ENTRYPRODUCT = "/entry-product";
	public static final String RESOURCE_TRANSFERS = RESOURCE_GENERIC + "/transfers";
	public static final String RESOURCE_TRANSFERS_TRANSFER = "/transfer";
	public static final String RESOURCE_AUTHORITYS_AUTHORITY_USER = "/user";
	public static final String RESOURCE_GENERIC_ID = "/{id}";
	public static final String RESOURCE_GENERIC_NRO_DOCUMENT = "/nro-document/{nro_document}";
	public static final String RESOURCE_FIND_ALL = "/findAll";
	public static final String RESOURCE_GENERIC_FILTER = "/filter";
	public static final String RESOURCE_FIND_ALL_DISTRIBUTION = "/finAllDistribution/{id}";
	public static final String RESOURCE_FIND_ALL_TYPE = "/findAllType/{id}";
	public static final String RESOURCE_USER_ID = "/{iduser}";
	public static final String RESOURCE_SALE_ID = "/{idSale}";
	public static final String RESOURCE_AUTHORITYS = RESOURCE_GENERIC + "/authorities";
	public static final String RESOURCE_DTENTRYPRODUCTS = RESOURCE_GENERIC + "/dt-entry-products";
	public static final String RESOURCE_DTENTRYPRODUCTS_DTENTRYPRODUCT = "/dt-entry-product";
	public static final String RESOURCE_DTENTRYPRODUCT_FIND_IDWHAREHOUSE = "/findByIdwharehouse/{id}";
	public static final String RESOURCE_SALES = RESOURCE_GENERIC + "/sales";
	public static final String RESOURCE_SALES_SALE = "/sale";
	public static final String RESOURCE_DTSALES = RESOURCE_GENERIC + "/dt-sales";
	public static final String RESOURCE_DTSALES_DTSALE = "/dt-sale";
	public static final String RESOURCE_VAUCHERS = RESOURCE_GENERIC + "/vauchers";
	public static final String RESOURCE_VAUCHERS_VAUCHER = "/vaucher";
	public static final String RESOURCE_VAUCHERS_VAUCHER_TICKET = "/ticket/{idSale}";
	public static final String RESOURCE_DASHBOARS = RESOURCE_GENERIC + "/dasboards";
	public static final String RESOURCE_DASHBOARS_DASHBOAR = "/dasboard";
	public static final String RESOURCE_REPORT_SALE_X_DATE = "/sale-x-date";
	public static final String RESOURCE_FILES = RESOURCE_GENERIC + "/files";
	public static final String RESOURCE_FILES_FILE = "/file";
	public static final String RESOURCE_PRODUCTIMGS = RESOURCE_GENERIC + "/productImgs";
	public static final String RESOURCE_PRODUCTIMGS_PRODUCTIMG = "/productImg";
	public static final String RESOURCE_PRODUCT_ID = "/{idProduct}";
	public static final String RESOURCE_VALIDATIONS = RESOURCE_GENERIC + "/validations";
	public static final String RESOURCE_VALIDATIONS_VALIDATION = "/validation";
	public static final String RESOURCE_VALIDATIONS_VALIDATION_SENDCODE = "/send-mail";

	// =============================================================================================
	// PATH DEL SISTEMA SYSCE FROENT-END
	// =============================================================================================
	public static final String PATH_FROTEND_SYSCE = "*";

	// =============================================================================================
	// NOMBRE DE LOS PROCEDIMIENTOS ALMACENADOS
	// =============================================================================================

	public static final String SP_SEARCH_PRODUCT_KEY_WORD = "select * from crce.sp_search_word_key_product(?1,?2)";
	public static final String SP_SEARCH_PRODUCT_KEY_WORD_COUNT = "select count(*) from crce.sp_search_word_key_product(?1,?2)";
	public static final String SP_SEARCH_PROVIDER_KEY_WORD = "select * from crce.sp_search_word_key_provider(?1,?2)";
	public static final String SP_SEARCH_PROVIDER_KEY_WORD_COUNT = "select count(*) from crce.sp_search_word_key_provider(?1,?2)";
	public static final String SP_SEARCH_USER_KEY_WORD = "select * from crcegu.sp_search_word_key_user(?1)";
	public static final String SP_SEARCH_USER_KEY_WORD_COUNT = "select count(*) from crcegu.sp_search_word_key_user(?1)";
	public static final String SP_SEARCH_AUTHORITY_MINUS = "select * from crcegu.sp_minus_authority(?1,?2)";
	public static final String SP_SEARCH_AUTHORITY_MINUS_COUNT = "select count(*) from crcegu.sp_minus_authority(?1,?2)";
	public static final String SP_EXIST_USER_AUTHORITY = "select * from crcegu.sp_exists_user_authority(?1,?2,?3)";
	public static final String SP_SAVE_USER_AUTHORITY = "select * from crcegu.sp_register_user_authority(?1,?2,?3)";
	public static final String SP_SEARCH_WORD_KEY_DT_ENTRY_PRODUCT = "select * from crce.sp_search_word_key_dt_entry_product(?1,?2,?3)";
	public static final String SP_SEARCH_WORD_KEY_DT_ENTRY_PRODUCT_COUNT = "select count(*) from crce.sp_search_word_key_dt_entry_product(?1,?2,?3)";
	public static final String SP_SAVE_DT_TRANSFER = "select * from crce.sp_register_dt_transfer(?1,?2,?3,?4,?5,?6)";
	public static final String SP_SEARCH_KEY_WORD_DT_TRANSFER = "select * from crce.sp_search_word_key_product_transfer(?1,?2,?3)";
	public static final String SP_SEARCH_CUSTOMER_KEY_WORD = "select * from crce.sp_search_word_key_custommer(?1,?2)";
	public static final String SP_SEARCH_CUSTOMER_KEY_WORD_COUNT = "select count(*) from crce.sp_search_word_key_custommer(?1,?2)";
	public static final String SP_SEARCH_PRODUCT_SALE = "select * from crce.sp_search_sale_parameter(?1,?2,?3,?4,?5)";
	public static final String SP_SEARCH_PRODUCT_SALE_COUNT = "select count(*) from crce.sp_search_sale_parameter(?1,?2,?3,?4,?5)";
	public static final String SP_FINDBY_DAILY_SALE = "{call crce.sp_get_daily_sale(?,?,?)}";
	public static final String SP_FINDBY_MONTH_SALE = "{call crce.sp_get_month_sale(?,?,?)}";
	public static final String VN_PASSWORDCHANGEFIRSTLOGIN = "1";
	public static final String VN_NOT_PASSWORDCHANGEFIRSTLOGIN = "0";
	public static final int QT_NUMBER_ATTEMPTS_DEFAULT = 0;
	public static final String VN_CODE_SECURITY_POLICY = "CODSP001";
	public static final String VN_PREFIX_AUTHORITY = "ROLE_";
	public static final String VN_EMPTY = "";
	
	
	// =============================================================================================
	// NOMBRE DE LAS TABLAS
	// =============================================================================================
	
	public static final String TB_SYS_BOTICA_USER_AUTHORITY = "adm_user_authority";
	public static final String TB_SYS_BOTICA_USER = "adm_user";
	public static final String TB_SYS_BOTICA_PRODUCT_IMG = "adm_product_img";
	public static final String TB_SYS_BOTICA_CUSTOMER = "adm_customer";
	public static final String TB_SYS_BOTICA_AUTHORITY = "adm_authority";
	public static final String TB_SYS_BOTICA_WHAREHOUSE = "adm_wharehouse";
	public static final String TB_SYS_BOTICA_TRANSFER = "adm_transfer";
	public static final String TB_SYS_BOTICA_DT_TRANSFER = "adm_dt_transfer";
	public static final String TB_SYS_BOTICA_WHAREHOUSE_SUBSIDIARY = "adm_wharehouse_subsidiary";
	public static final String TB_SYS_BOTICA_ENTRY_PRODUCT = "adm_entry_product";
	public static final String TB_SYS_BOTICA_DT_ENTRY_PRODUCT = "adm_dt_entry_product";
	public static final String TB_SYS_BOTICA_ENTRY_DOCUMENT = "util_entry_document";
	public static final String TB_SYS_BOTICA_TYPE_DOCUMENT = "util_type_document";
	public static final String TB_SYS_BOTICA_PROOF_PAYMENT = "util_proof_payment";
	public static final String TB_SYS_BOTICA_HTML_TEMPLATE = "util_html_template";
	public static final String TB_SYS_BOTICA_SALE = "adm_sale";
	public static final String TB_SYS_BOTICA_DT_SALE = "adm_dt_sale";
	
	// =============================================================================================
	// NOMBRE DE LAS VISTAS
	// =============================================================================================
	public static final String VT_SYS_BOTICA_DASHBOARD = "v_dashboard";
	public static final String VT_SYS_BOTICA_SALE_DATE = "v_sale_date";
	
	// =============================================================================================
	// NOMBRE DE LOS ESQUEMAS
	// =============================================================================================
	
	public static final String SCHEMA_SYS_BOTICA_CRCEGU = "crcegu";
	public static final String SCHEMA_SYS_BOTICA_CRCE = "crce";
	public static final String SCHEMA_SYS_BOTICA_CRCEUTIL = "crceutil";
	
	// =============================================================================================
	// PATH DE LOS RECURSOS DE REPORTES
	// =============================================================================================
	public static final String RESOURCE_VAUCHER_TICKET = "cpag_ticket_maq_registradora";
	public static final String RESOURCE_REPORT_SALE_DATE = "rport_total_sale_x_date";
	
	// =============================================================================================
	// EXTENCIONES DE LOS ARCHIVOS
	// =============================================================================================
	
	public static final String FILE_EXT_PDF = ".pdf";
	
	// =============================================================================================
	// DATA GENERIC REPORT TICKET
	// =============================================================================================
	
	public static final String TICKET_CRCE_NAME_DRUGSTORE = "SALUD SESYFARMA";
	public static final String TICKET_CRCE_DISTRICT_DRUGSTORE = "AYACUCHO - HUAMANGA - AYACUCHO";
	public static final String TICKET_CRCE_ADDRESS_DRUGSTORE = "AV. 26 DE ENERO 265";

	

}
