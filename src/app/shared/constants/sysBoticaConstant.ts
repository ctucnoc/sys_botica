export abstract class SysBoticaConstant {


    // este sistemas esta hecho por cr para ce
    static readonly MESSAGE_LOADING_SING_IN: string = 'Iniciando Sessión';
    static readonly MESSAGE_LOADING_REGISTER: string = 'Registrando...';
    static readonly MESSAGE_OBJECT_UPDATE: string = 'Correctamente Actualizado';
    static readonly MESSAGE_OBJECT_ADD: string = 'Correctamente Registrado';
    static readonly MESSAGE_OBJECT_DELETE: string = 'Correctamente Eliminado';

    // recursos del sistema
    static readonly RESOURCE_HOME_INIT: string = 'intranet/dashboard';

    // recursos del sistema
    static readonly RESOURCE_DEFAULT_PROOF_PAYMENT: number = 5;

    // TAMANO DE ELEMENTOS POR PAGINA
    static readonly PAG_SIZE_INITIAL: number = 5;
    static readonly PAG_SIZE_INITIAL_SALE: number = 3;
    static readonly PAG_SIZE_INITIAL_INFINIT_SCROLBAR: number = 10;
    static readonly PAG_SIZE_INITIAL_SEARCH: number = 3;
    static readonly PAG_NRO_INITIAL: number = 0;
    static readonly NRO_ELEMENT_DEFAULT: number = 1;
    static readonly NRO_VALUE_RUC: number = 6;
    static readonly VC_EMPTY: string = '';
    static readonly VC_OPERATION_UPDATE: string = 'UPDATE';
    static readonly VC_OPERATION_ADD: string = 'ADD';
    static readonly VC_REQUIRED: string = '1';

    // RESOURCE 
    static readonly RESOURCE_PAG_SING_IN: string = '/';

    // title page
    static readonly TITLE_PAGE_PRODUCT: string = ' | Producto';
    static readonly TITLE_PAGE_CATEGORY: string = ' | Category';
    static readonly TITLE_PAGE_CUSTOMER: string = ' | Cliente';
    static readonly TITLE_PAGE_MARK: string = ' | Marca';
    static readonly TITLE_PAGE_UNIT: string = ' | Unidad Medida';
    static readonly TITLE_PAGE_USER: string = ' | Usuario';
    static readonly TITLE_PAGE_USER_SUBSIDIARY: string = ' | Usuario por Sucursal';
    static readonly TITLE_PAGE_USER_AUTHORITY: string = ' | Usuario por Rol';
    static readonly TITLE_PAGE_SECURITY_POLICY: string = ' | Politicas de Seguridad';
    static readonly TITLE_PAGE_DASHBOARD: string = ' | dashboard';
    static readonly TITLE_PAGE_SING_IN: string = ' | Inicio Sessión';
    static readonly TITLE_PAGE_SING_OUT: string = ' | Cerrar Sessión';
    static readonly TITLE_PAGE_AUTHORITY: string = ' | Rol';
    static readonly TITLE_PAGE_WHAREHOUSE_SUBSIDIARY: string = ' | Almacén por Sucursal';
    static readonly TITLE_PAGE_PROVAIDER: string = ' | Proveedor';
    static readonly TITLE_PAGE_WHAREHOUSE: string = ' | Almacén';
    static readonly TITLE_PAGE_OUT_SUB_WAREHOUSE: string = ' | Salida a Sub Almacén';
    static readonly TITLE_PAGE_ENTRY_PRODUCT: string = ' | Ingreso de Productos';
    static readonly TITLE_PAGE_SALE_PRODUCT: string = ' | Realizar Venta';
    static readonly TITLE_PAGE_SALE_PRODUCT_SEARCH: string = ' | Consultar Venta';
    static readonly TITLE_PAGE_REPORT_SALE_DATE: string = ' | Reporte Venta';

    // localstorage
    static readonly STORAGE_ACCESS_TOKEN: string = 'AVCR1M234GHHDLLSD';
    static readonly STORAGE_USER_NAME: string = 'AVCR1M234GHHDLLLD';
    static readonly STORAGE_AUTHORITIES: string = 'AVCR13234GHHDLLLD';
    static readonly STORAGE_IS_LOGGED_IN: string = 'AVCR13234GHHLLLDD';

    // msg para el usuario final
    static readonly MSG_NOT_FOUND_USER: string = 'Usuario no Encontrado';
    static readonly MSG_INTERNAL_SERVER_ERROR: string = 'Error Interno Servidor';

    static readonly MSG_TOTAL_PRODUCT:string = 'Total Productos';
    static readonly MSG_TOTAL_UNIT:string = 'Total unidades';
    static readonly MSG_TOTAL_PROVIDER:string = 'Total proveedores';
    static readonly MSG_TOTAL_CATEGORY:string = 'Total categoria';
    static readonly MSG_TOTAL_MARK:string = 'Total marcas';
    static readonly MSG_TOTAL_CUSTOMER:string = 'Total clientes';

}

export const ARRAY_DOS_OPCIONES = [
    {
        id: '1',
        description: 'SI',
    },
    {
        id: '0',
        description: 'NO',
    }
];

export const ARRAY_TYPE_WHAREHOUSE: any[] = [
    {
        id: '1',
        description: 'DISTRIBUCIÓN',
    },
    {
        id: '0',
        description: 'SUB ALMACÉN',
    }
];

export const FORMATS = {
    parse: {
        dateInput: 'L'
    },
    display: {
        dateInput: 'DD-MM-YYYY',
        monthYearLabel: 'MMM YYYY',
        dateA11yLabel: 'L',
        monthYearA11yLabel: 'MMMM YYYY'
    }
};