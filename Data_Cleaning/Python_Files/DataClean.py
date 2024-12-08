import pandas as pd
import os

input_directory = '/Users/minalnakawe/SER531-Intellihealth/Data_Cleaning/Datasets/original'

output_directory = '/Users/minalnakawe/SER531-Intellihealth/Data_Cleaning/Datasets/cleaned'

csv_files = [file for file in os.listdir(input_directory) if file.endswith('.csv')]

for csv_file in csv_files:
    file_path = os.path.join(input_directory, csv_file)
    df = pd.read_csv(file_path)

    df = df.head(3000)

    def apply_logic_to_Cardio(df):
        # Gender Mapping
        gender_mapping = {1: 'Female', 2: 'Male'}
        df['GENDER'] = df['GENDER'].replace(gender_mapping)

        # Blood Pressure
        df['AP_HIGH'] = df['AP_HIGH'].apply(lambda x: 'Yes' if x > 120 else 'No')

        # Cholesterol Levels
        cholesterol_mapping = {1: 'No', 2: 'Yes', 3: 'Yes'}
        df['CHOLESTEROL'] = df['CHOLESTEROL'].replace(cholesterol_mapping)

        # Glucose Levels
        glucose_mapping = {1: 'No', 2: 'Yes', 3: 'Yes'}
        df['GLUCOSE'] = df['GLUCOSE'].replace(glucose_mapping)

        # Smoke
        smoke_mapping = {0: 'No', 1: 'Yes'}
        df['SMOKE'] = df['SMOKE'].replace(smoke_mapping)

        cardio_mapping = {0: 'No', 1: 'Yes'}
        df['CARDIO_DISEASE'] = df['CARDIO_DISEASE'].replace(cardio_mapping)

        # Columns to Remove
        columns_to_remove = ['HEIGHT', 'WEIGHT', 'AP_LOW', 'ALCOHOL', 'PHYSICAL_ACTIVITY']
        return df.drop(columns=columns_to_remove)

    def apply_logic_to_COPD(df):
        # Gender Mapping
        gender_mapping = {0: 'Female', 1: 'Male'}
        df['gender'] = df['gender'].replace(gender_mapping)
        # Smoke
        smoke_mapping = {1: 'No', 2: 'Yes'}
        df['smoking'] = df['smoking'].replace(smoke_mapping)
        # Glucose Levels
        glucose_mapping = {0: 'No', 1: 'Yes'}
        df['Diabetes'] = df['Diabetes'].replace(glucose_mapping) 
        # Hypertension
        BP_mapping = {0: 'No', 1: 'Yes'}
        df['hypertension'] = df['hypertension'].replace(BP_mapping)
        # Muscular
        muscular_mapping = {0: 'No', 1: 'Yes'}
        df['muscular'] = df['muscular'].replace(muscular_mapping)
        # IHD
        IHD_mapping = {0: 'No', 1: 'Yes'}
        df['IHD'] = df['IHD'].replace(IHD_mapping)
         # Columns to Remove
        columns_to_remove = ['un', 'ID', 'PackHistory', 'MWT1', 'MWT2', 'FEV1', 'FEV1PRED', 'FVC', 'FVCPRED', 'CAT', 'HAD', 'SGRQ', 'AGEquartiles', 'copd', 'AtrialFib']
        return df.drop(columns=columns_to_remove)

    def apply_logic_to_CoVID(df):
        # Gender Mapping
        gender_mapping = {1: 'Female', 2: 'Male'}
        df['SEX'] = df['SEX'].replace(gender_mapping)
        # PNEUMONIA
        PNEUMONIA_mapping = {1: 'No', 2:'No', 99:'Yes'}
        df['PNEUMONIA'] = df['PNEUMONIA'].replace(PNEUMONIA_mapping)
        # DIABETES
        DIABETES_mapping = {1: 'No', 2: 'No', 98:'Yes'}
        df['DIABETES'] = df['DIABETES'].replace(DIABETES_mapping)
        # COPD
        COPD_mapping = {1: 'No', 2: 'No', 98:'Yes'}
        df['COPD'] = df['COPD'].replace(COPD_mapping)
        # HIPERTENSION
        HIPERTENSION_mapping = {1: 'No', 2: 'No', 98:'Yes'}
        df['HIPERTENSION'] = df['HIPERTENSION'].replace(HIPERTENSION_mapping)
        # OTHER_DISEASE
        OTHER_DISEASE_mapping = {1: 'No', 2: 'No', 98:'Yes'}
        df['OTHER_DISEASE'] = df['OTHER_DISEASE'].replace(OTHER_DISEASE_mapping)
        # CARDIOVASCULAR
        CARDIOVASCULAR_mapping = {1: 'No', 2: 'No', 98:'Yes'}
        df['CARDIOVASCULAR'] = df['CARDIOVASCULAR'].replace(CARDIOVASCULAR_mapping)
        # OBESITY
        OBESITY_mapping = {1: 'No', 2: 'No', 98:'Yes'}
        df['OBESITY'] = df['OBESITY'].replace(OBESITY_mapping)
        # TOBACCO
        TOBACCO_mapping = {1: 'No', 2: 'No', 98:'Yes'}
        df['TOBACCO'] = df['TOBACCO'].replace(TOBACCO_mapping)

         # Columns to Remove
        columns_to_remove = ['USMER', 'MEDICAL_UNIT', 'PATIENT_TYPE', 'DATE_DIED', 'INTUBED', 'PREGNANT', 'ASTHMA', 'INMSUPR', 'RENAL_CHRONIC', 'CLASIFFICATION_FINAL', 'ICU']
        return df.drop(columns=columns_to_remove)
       

    if "Cardio_dataset" in csv_file:
        df_cleaned = apply_logic_to_Cardio(df)
    elif "COPD_dataset" in csv_file:
        df_cleaned = apply_logic_to_COPD(df)
    elif "Covid_dataset" in csv_file:
        df_cleaned = apply_logic_to_CoVID(df)
    else:
        df_cleaned = df

    output_file_path = os.path.join(output_directory, f'cleaned_{csv_file}')
    df_cleaned.to_csv(output_file_path, index=False)
