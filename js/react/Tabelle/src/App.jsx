import './App.css';

function App() {

    const modelHeader = ['id', 'model', 'type'];
    const modelBody = [
        [1, 'Corsa', 'small'],
        [2, 'Astra', 'family'],
        [3, 'Mokka', 'SUV'],
        [4, 'Movano', 'transporter'],
    ];

    const headerItems = modelHeader.map((head, headId) => <th key={headId}>{head}</th>);
    const bodyItems = modelBody.map((row, rowId) => (
        <tr key={rowId}>
            {row.map((value, valueId) => <td key={valueId}>{value}</td>)}
        </tr>
    ));

  return (
    <>
        <div>
            <h2>Car models</h2>
            <table>
                <thead>
                    <tr>{headerItems}</tr>
                </thead>
                <tbody>{bodyItems}</tbody>
            </table>
        </div>
    </>
  )
}

export default App
