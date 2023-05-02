window.addEventListener('load', () => {

    const params = (new URL(document.location)).searchParams;
    const aid = params.get('aid');

    const aid = sessionAttribute.getItem('aid');

    document.getElementById('result-aid').innerHTML = aid;

})

function handleSubmit() {
    const aid = document.getElementById('aid').value;
    sessionAttribute.setItem("aid", aid);
    return;
}