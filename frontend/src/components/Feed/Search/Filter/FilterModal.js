import { Box, FormLabel, Grid, Modal } from "@mui/material";
import GrButton from "components/common/GrButton";

import { useState } from "react";
import { useForm } from "react-hook-form";
import Checkboxes from "./Checkboxes";
import FilterRadio from "./FilterRadio";
import { age, gender, interest, location } from "../initData";

const modalStyle = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 750,
  bgcolor: "background.paper",
  border: "0.5px solid #888",
  p: 4,
};

const labelStyle = {
  color: "black",
  fontWeight: "600",
};

function FilterModal({ open, handleClose, ...props }) {
  const [interestRadio, setInterestRadio] = useState("all");
  const [genderRadio, setGenderRadio] = useState("all");
  const [ageRadio, setAgeRadio] = useState("all");
  const [locationRadio, setLocationRadio] = useState("all");

  const { control, handleSubmit } = useForm({
    defaultValues: {
      interest: interest,
      gender: gender,
      age: age,
      location: location,
    },
  });

  const onModalClose = () => {
    handleClose();
  };

  const onSubmit = (data) => {
    console.log(data);
  };

  return (
    <Modal
      open={open}
      onClose={onModalClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={modalStyle}>
        <form>
          <Grid className="filter-modal" container direction="column">
            <Grid
              className="filter-modal__wrapper"
              container
              direction="column"
            >
              <FormLabel
                className="filter-modal__label"
                id="filter-modal__interest"
                sx={labelStyle}
              >
                운동 종목
              </FormLabel>
              <FilterRadio radio={interestRadio} setRadio={setInterestRadio} />
              {interestRadio === "custom" && (
                <Checkboxes
                  options={interest}
                  control={control}
                  name="interest"
                  radio={interestRadio}
                  xs={2.4}
                />
              )}
            </Grid>
            <Grid className="filter-modal--middle" container>
              <Grid item xs={6}>
                <Grid
                  className="filter-modal__wrapper"
                  container
                  direction="column"
                >
                  <FormLabel
                    className="filter-modal__label filter-modal__middle"
                    id="filter-modal__gender"
                    sx={labelStyle}
                  >
                    성별
                  </FormLabel>
                  <FilterRadio radio={genderRadio} setRadio={setGenderRadio} />
                  {genderRadio === "custom" && (
                    <Checkboxes
                      options={gender}
                      control={control}
                      name="gender"
                      radio={genderRadio}
                      xs={3}
                    />
                  )}
                </Grid>
              </Grid>
              <Grid item xs={6}>
                <Grid
                  className="filter-modal__wrapper"
                  container
                  direction="column"
                >
                  <FormLabel
                    className="filter-modal__label filter-modal__middle"
                    id="filter-modal__age"
                    sx={labelStyle}
                  >
                    연령대
                  </FormLabel>
                  <FilterRadio radio={ageRadio} setRadio={setAgeRadio} />
                  {ageRadio === "custom" && (
                    <Checkboxes
                      options={age}
                      control={control}
                      name="age"
                      radio={ageRadio}
                    />
                  )}
                </Grid>
              </Grid>
            </Grid>
            <Grid
              className="filter-modal__wrapper"
              container
              direction="column"
            >
              <FormLabel
                className="filter-modal__label"
                id="filter-modal__location"
                sx={labelStyle}
              >
                지역
              </FormLabel>
              <FilterRadio radio={locationRadio} setRadio={setLocationRadio} />
              {locationRadio === "custom" && (
                <Checkboxes
                  options={location}
                  control={control}
                  name="age"
                  radio={locationRadio}
                />
              )}
            </Grid>
            <Grid className="filter-modal__button-wrapper" item>
              <GrButton
                className="filter-modal__button"
                xs={1}
                variant="contained"
                onClick={handleSubmit(onSubmit)}
              >
                필터 설정
              </GrButton>
            </Grid>
          </Grid>
        </form>
      </Box>
    </Modal>
  );
}

export default FilterModal;
