import { AgGridColumn, AgGridReact } from 'ag-grid-react';
import { useEffect, useState, useRef } from 'react';
import 'ag-grid-enterprise';

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';

const App = () => {
    const [rowData, setRowData] = useState([]);
    const gridRef = useRef(null);
    useEffect(() => {
        fetch('http://localhost:8080/api/transactions')
            .then(result => result.json())
            .then(rowData => setRowData(rowData))
    }, []);
    
    const onButtonClick = e => {
        const selectedNodes = gridRef.current.api.getSelectedNodes()
        const selectedData = selectedNodes.map(node => node.data)
        const selectedDataStringPresentation = selectedData.map(node => `${node.transactionId} ${node.fromAddress} ${node.toAddress}`).join(', ')
        alert(`Selected nodes: ${selectedDataStringPresentation}`)
    }


    return (
        <div className="ag-theme-alpine" style={{ height: 400, width: 1000 }}>
            <button onClick={onButtonClick}> Approve selected transactions</button>
            <AgGridReact
                ref={gridRef}
                rowData={rowData}
                rowSelection="multiple">
                <AgGridColumn field="transactionId" sortable={true} filter={true} checkboxSelection={ true }></AgGridColumn>
                <AgGridColumn field="fromAddress" sortable={true} filter={true}></AgGridColumn>
                <AgGridColumn field="toAddress" sortable={true} filter={true}></AgGridColumn>
                <AgGridColumn field="note" sortable={true} filter={true}></AgGridColumn>
                <AgGridColumn field="amount" sortable={true} filter={true}></AgGridColumn>
                <AgGridColumn field="userId" sortable={true} filter={true}></AgGridColumn>
                <AgGridColumn field="status" sortable={true} filter={true}></AgGridColumn>
            </AgGridReact>
        </div>
    );
};

export default App;