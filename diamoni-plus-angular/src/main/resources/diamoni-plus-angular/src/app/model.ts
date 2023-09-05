interface FaultType {
  chars: string;
  "string": string;
  valueType: string;
}

export interface ValidationFault {
  fault: FaultType
}

export interface LoginReqMsgType {
  username: string;
  password: string;
}

export interface LoginRespMsgType {
  jwt: string;
  userId: string;
}

export interface RegisterReqMsgType {
  username: string;
  password: string;
  passwordConfirmation: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  desiredRole: string;
  avatar?: ImageFileType;
}

export interface RegisterRespMsgType {
  jwt: string;
}

export interface RetrieveUserInfoRespMsgType {
  username: string;
  role: string;
  avatar?: ImageFileType;
  firstName: string;
  lastName: string;
}

export interface ImageFileType {
  binaryIdentification?: string;
  data: string;
  filename: string;
  mime: string;
}
