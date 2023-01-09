export function resizeImage(base64Image: string): Promise<string> {
    const maxSize = 497;

    const img = new Image(); // create a image
    img.src = base64Image; // result is base64-encoded Data URI

    return new Promise(resolve => {
        img.onload = (el: any) => {
            const elem = document.createElement('canvas');
            const aspectRatio = el.target.width / el.target.height;

            if (img.width > img.height) {
                elem.width = maxSize;
                elem.height = maxSize / aspectRatio;
            }

            if (img.height > img.width) {
                elem.height = maxSize;
                elem.width = maxSize * aspectRatio;
            }

            const ctx: any = elem.getContext('2d');
            ctx.drawImage(el.target, 0, 0, elem.width, elem.height);

            const srcEncoded = ctx.canvas.toDataURL(el.target, 'image/jpeg');

            resolve(srcEncoded);

        };
    });
}

export function validExtend(filename: string): boolean {
    const extn = filename.split(".").pop();
    const validFormats = ['jpg', 'jpeg', 'png'];
    let rpta: boolean = false;
    validFormats.forEach((bean) => {
        console.log('hola mundo', bean);
        if (extn == bean) {
            rpta = true;
        }
    })
    return rpta;
}