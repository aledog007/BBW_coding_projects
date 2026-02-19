import money from '/Waehrungen.jpg';

export default function Picture() {

    return (
        <>
        <h1>Währungen umrechnen</h1>
        <img src={money} className="money" alt="Paund ,Dollar, Yen, Euro" />
        </>
    )
}