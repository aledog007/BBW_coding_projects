import DataTable from "react-data-table-component";


function TabeleExample () {

    const columns = [
        { 
            title: 'id',
            selector: row => row.id,
            sortable: true,
        },
        { 
            title: 'model',
            selector: row => row.model,
            sortable: true,
        },
        { 
            title: 'type',
            selector: row => row.type,
            sortable: true,
        },
    ];

    const data = [
        { id: 1, model: 'Corsa', type: 'small' },
        { id: 2, model: 'Astra', type: 'family' },
        { id: 3, model: 'Mokka', type: 'SUV' },
        { id: 4, model: 'Movano', type: 'transporter' },
    ];

    return (
        <>
            <h2>second table</h2>
            <DataTable
                columns={columns}
                data={data}
                selectableRows
            />
        </>
    );
};

export default TabeleExample;