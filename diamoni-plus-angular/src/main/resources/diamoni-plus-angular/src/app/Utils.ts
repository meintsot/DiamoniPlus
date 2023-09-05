import {ImageFileType} from "./model";

export class Utils {

  private static readonly PRECEDING_DATA = 'base64,';

  static removeDataURL(data: string): string {
    if (data.includes(this.PRECEDING_DATA)) {
      return data.substring(data.indexOf(this.PRECEDING_DATA) + this.PRECEDING_DATA.length);
    }

    return data;
  }

  static createDataUrl(imageFile: ImageFileType): string {
    return 'data:' + imageFile.mime + ';' + this.PRECEDING_DATA + imageFile.data;
  }
}
