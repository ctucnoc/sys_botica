export function compressImage(img:any) {
    const canvas = document.createElement('canvas');

    canvas.width = img.width;
    canvas.height = img.height;

    const ctx: any = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

    return canvas.toDataURL('image/jpeg', 0.5);
}

export function b64toFile(dataURI: string, fileName: string) {
    const byteString = atob(dataURI.split(',')[1]);
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);

    for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    return new File([new Blob([ab], { type: 'image/jpeg' })], fileName);
}